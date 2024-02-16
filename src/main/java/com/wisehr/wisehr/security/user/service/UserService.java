package com.wisehr.wisehr.security.user.service;

import com.wisehr.wisehr.security.user.entity.User;
import com.wisehr.wisehr.security.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserService {


    private final UserRepository userRepository;
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }


    public Optional<User> findUserByCode(int code) {

        return userRepository.findByMemCode(code);
    }

    @Transactional
    public User registUser(User user) {

        return userRepository.save(user);
    }
}

