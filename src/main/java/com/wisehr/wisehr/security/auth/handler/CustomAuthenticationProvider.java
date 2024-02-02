package com.wisehr.wisehr.security.auth.handler;

import com.wisehr.wisehr.security.auth.model.DetailsUser;
import com.wisehr.wisehr.security.auth.service.DetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private DetailsService detailsService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder; // 비밀번호가 맞는지 확인하기 위해

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        UsernamePasswordAuthenticationToken loginToken = (UsernamePasswordAuthenticationToken) authentication;

        String id = loginToken.getName();
        String pass = (String) loginToken.getCredentials();

        DetailsUser detailsUser = (DetailsUser) detailsService.loadUserByUsername(id);

        if(!passwordEncoder.matches(pass, detailsUser.getPassword())){
            throw new BadCredentialsException(pass + "는 비밀번호가 아닙니다.");
        }
        return new UsernamePasswordAuthenticationToken(detailsUser, pass, detailsUser.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
