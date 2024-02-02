package com.wisehr.wisehr.security.auth.config;

import com.wisehr.wisehr.security.auth.filter.CustomAuthenticationFilter;
import com.wisehr.wisehr.security.auth.filter.JwtAuthorizationFilter;
import com.wisehr.wisehr.security.auth.handler.CustomAuthFailUserHandler;
import com.wisehr.wisehr.security.auth.handler.CustomAuthSuccessHandler;
import com.wisehr.wisehr.security.auth.handler.CustomAuthenticationProvider;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
    /***
     * 정적 자원에 대한 인증된 사용자의 접근을 설정하는 메소드
     */
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    /***
     * security filter chain 설정
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf(AbstractHttpConfigurer::disable)
                .addFilterBefore(jwtAuthorizationFilter(), BasicAuthenticationFilter.class)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))     // 세션인증 끄기
                .formLogin(form -> form.disable())
                .addFilterBefore(customAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .httpBasic(basic -> basic.disable());

        return http.build();
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