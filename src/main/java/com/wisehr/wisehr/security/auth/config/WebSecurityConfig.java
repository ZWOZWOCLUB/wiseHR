package com.wisehr.wisehr.security.auth.config;

import com.wisehr.wisehr.security.auth.filter.CustomAuthenticationFilter;
import com.wisehr.wisehr.security.auth.filter.JwtAuthorizationFilter;
import com.wisehr.wisehr.security.auth.handler.*;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
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
    public WebSecurityCustomizer webSecurityCustomizer(){
        return (web) -> web.ignoring().requestMatchers("/css/**", "/js/**", "/images/**",
                "/lib/**", "/career/**", "/certificate/**", "/dataFomats/**", "/degree/**", "/etcDocumentFile/**", "/memberFiles/**", "/noticeFiles/**", "/profile/**", "/salary/**", "/sign/**");
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
                // 세션 관리 정책 설정: STATELESS로 설정하여 세션을 사용하지 않음
                .sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // 폼 로그인 및 HTTP 기본 인증 비활성화
                .formLogin(formLogin -> formLogin.disable())
                .httpBasic(httpBasic -> httpBasic.disable())
                // 사용자 정의 인증 필터 추가
                .addFilterBefore(customAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
//        http
//                // URL 별 접근 권한 설정
//                .authorizeRequests()
//                .requestMatchers("/approval/**").hasRole("ADMIN")
//                .requestMatchers("/user/**").hasAnyRole("USER", "ADMIN")
//                .requestMatchers("/public/**").permitAll()
//                .anyRequest().authenticated()
//                .and();
        http
                // 로그아웃 설정
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .logoutSuccessHandler(logoutSuccessHandler())
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


}

