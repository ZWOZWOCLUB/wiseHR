package com.wisehr.wisehr.security.auth.handler;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import java.util.Collections;

public class CustomUserDetaileService {

    @Service
    public class CustomUserDetailsService implements UserDetailsService {

        @Override
        public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
            // 여기에서 데이터베이스 또는 다른 저장소에서 사용자 정보를 조회합니다.
            // 예제를 위해 하드코딩된 사용자 정보를 반환합니다.
            if ("user".equals(username)) {
                return new org.springframework.security.core.userdetails.User(
                        "user", // 사용자 이름
                        "password", // 비밀번호
                        Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"))); // 권한
            } else {
                throw new UsernameNotFoundException("User not found with username: " + username);
            }
        }
    }
}
