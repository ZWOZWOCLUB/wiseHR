package com.wisehr.wisehr.security.user.controller;

import com.wisehr.wisehr.security.user.entity.User;
import com.wisehr.wisehr.security.user.repository.UserRepository;
import com.wisehr.wisehr.security.user.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;


@RestController
public class UserController {

//    private final UserRepository userRepository;

    private final UserService userService;
    private final BCryptPasswordEncoder PasswordEncoder;

    public UserController(UserService userService, BCryptPasswordEncoder PasswordEncoder) {
        this.userService = userService;
        this.PasswordEncoder = PasswordEncoder;
    }

    @PostMapping("/signup")
    public ResponseEntity signup(@RequestBody User user){
        System.out.println("user ============= " + user);
        user.setMemPassword(PasswordEncoder.encode(user.getMemPassword()));
        user.setMemStatus("Y");

        User value = userService.registUser(user);

        System.out.println("value ============= " + value);

        if(Objects.isNull(value)){
            return ResponseEntity.status(500).body("회원가입 실패");
        }else{
            return ResponseEntity.ok("회원가입 성공");
        }

    }
}
