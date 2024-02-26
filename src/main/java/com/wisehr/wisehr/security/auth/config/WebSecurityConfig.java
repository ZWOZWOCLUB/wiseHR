package com.wisehr.wisehr.security.auth.config;

import com.wisehr.wisehr.security.auth.filter.CustomAuthenticationFilter;
import com.wisehr.wisehr.security.auth.filter.JwtAuthorizationFilter;
import com.wisehr.wisehr.security.auth.handler.*;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig {
    /***
     * 정적 자원에 대한 인증된 사용자의 접근을 설정하는 메소드
     */
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers("/css/**", "/js/**", "/images/**",
                "/lib/**", "/career/**", "/certificate/**", "/dataFomats/**", "/degree/**", "/etcDocumentFile/**", "/memberFiles/**", "/noticeFiles/**", "/profile/**", "/salary/**", "/sign/**", "/memberFile/**");
    }

    /***
     * security filter chain 설정
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // CSRF 보호 비활성화
                .csrf(csrf -> csrf.disable())
                // JWT 인증 필터 추가
                .addFilterBefore(jwtAuthorizationFilter(), BasicAuthenticationFilter.class)
                // 세션 관리 설정: 세션 만료 시간, 세션 고정 방지 등
                .sessionManagement(sessionManagement -> sessionManagement
                        // 세션 만료 시간 설정 (5분)
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                        .maximumSessions(1) // 한 사용자당 최대 세션 수
                        .maxSessionsPreventsLogin(true) // 새 세션이 생성되는 경우 로그인 방지
                        .expiredUrl("/login?expired") // 세션이 만료된 경우 리다이렉트할 URL

                )
                // 폼 로그인 및 HTTP 기본 인증 비활성화
                .formLogin(formLogin -> formLogin.disable())
                .httpBasic(httpBasic -> httpBasic.disable())
                // 사용자 정의 인증 필터 추가
                .addFilterBefore(customAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                // 로그아웃 설정
                .logout(logout ->
                        logout
                                // 로그아웃 URL 설정
                                .logoutUrl("/logout")
                                // 로그아웃 성공 후 이동할 URL 설정
                                .logoutSuccessUrl("/login?logout")
                                // HTTP 세션 무효화 여부 설정
                                .invalidateHttpSession(true)
                                // 삭제할 쿠키 설정
                                .deleteCookies("JSESSIONID") // 필요한 경우 쿠키 삭제
                                // 로그아웃 성공 핸들러 설정
                                .logoutSuccessHandler(logoutSuccessHandler()) // 선택적
                )
                // 예외 처리 설정
                .exceptionHandling(exceptionHandling ->
                        // 권한이 없는 사용자에게 알림 보내기
                        exceptionHandling.accessDeniedHandler(customAccessDeniedHandler())
                );

        return http.build();
    }

    @Bean
    public LogoutSuccessHandler logoutSuccessHandler() {
        return (request, response, authentication) -> {
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write("{\"message\":\"로그아웃 성공\"}");

            System.out.println("authentication = " + authentication);
        };
    }


    /***
     * 사용자의 인증 요청을 가로채서 로그인 로직을 수행하는 필터
     * @return
     */
    @Bean
    public CustomAuthenticationFilter customAuthenticationFilter() {

        CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(authenticationManager());
        customAuthenticationFilter.setFilterProcessesUrl("/login");
        customAuthenticationFilter.setAuthenticationSuccessHandler(customAuthLoginSuccessHandler());
        customAuthenticationFilter.setAuthenticationFailureHandler(customAuthFailUserHandler());
        customAuthenticationFilter.afterPropertiesSet();
        return customAuthenticationFilter;
    }

    /***
     * spring security 기반의 사용자 정보가 맞지 않은 경우 수행하는 핸들러
     * @return
     */
    @Bean
    public CustomAuthFailUserHandler customAuthFailUserHandler() {
        return new CustomAuthFailUserHandler();
    }

    /***
     * spring security 기반의 사용자의 정보가 맞을 경우 결과를 수행하는 핸들러
     * @return
     */
    @Bean
    public CustomAuthSuccessHandler customAuthLoginSuccessHandler() {
        return new CustomAuthSuccessHandler();
    }


    /***
     * Authentization의 인증 메서드를 제공하는 메니저로 Provider의 인터페이스를 의미한다.
     * @return
     */
    @Bean
    public AuthenticationManager authenticationManager() {

        return new ProviderManager(customAuthenticationProvider());
    }

    /***
     * 사용자의 아이디와 패스워드를 DB와 검증하는 handler
     * @return
     */
    @Bean
    public CustomAuthenticationProvider customAuthenticationProvider() {
        return new CustomAuthenticationProvider();
    }

    /***
     * 사용자 요청 시 수행되는 메소드
     * @return
     */
    public JwtAuthorizationFilter jwtAuthorizationFilter() {

        return new JwtAuthorizationFilter(authenticationManager());
    }


    /***
     * 비밀번호를 암호화하는 인코더
     * @return
     */
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public AccessDeniedHandler customAccessDeniedHandler() {
        return (request, response, accessDeniedException) -> {
            // 여기에 접근 거부 로직을 구현
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.getWriter().write("권한없음");
        };
    }
}
