package com.wisehr.wisehr.security.user.controller;

import com.wisehr.wisehr.security.user.entity.User;
import com.wisehr.wisehr.security.user.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.wisehr.wisehr.security.common.utils.TokenUtils;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestController
public class UserController {

    private final UserService userService;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserController(UserService userService, BCryptPasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @Tag(name = "사용자 등록", description = "사용자 등록")
    @PostMapping("/signup")
    public ResponseEntity signup(@RequestBody User user) {
        System.out.println("user ============= " + user);
        user.setMemPassword(passwordEncoder.encode(user.getMemPassword()));
        user.setMemStatus("Y");

        User value = userService.registUser(user);

        System.out.println("value ============= " + value);

        if (Objects.isNull(value)) {
            return ResponseEntity.status(500).body("회원가입 실패");
        } else {
            return ResponseEntity.ok("회원가입 성공");
        }
    }
}
