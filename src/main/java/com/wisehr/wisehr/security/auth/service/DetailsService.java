package com.wisehr.wisehr.security.auth.service;

import com.wisehr.wisehr.security.auth.model.DetailsUser;
import com.wisehr.wisehr.security.user.service.UserService;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DetailsService implements UserDetailsService {

    private final UserService userService;

    public DetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 여기서는 username이 실제로는 code 값을 나타낸다고 가정합니다.
        // username을 정수로 변환하는 로직을 추가할 수 있습니다.
        try {
            int code = Integer.parseInt(username);
            return userService.findUserByCode(code)
                    .map(DetailsUser::new)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found with code: " + code));
        } catch (NumberFormatException e) {
            throw new UsernameNotFoundException("Invalid user code: " + username);
        }
    }
}

