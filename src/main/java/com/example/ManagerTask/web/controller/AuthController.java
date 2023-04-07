package com.example.ManagerTask.web.controller;

import com.example.ManagerTask.service.AuthService;
import com.example.ManagerTask.service.UserService;
import com.example.ManagerTask.web.dto.auth.JwtRequest;
import com.example.ManagerTask.web.dto.auth.JwtResponse;
import com.example.ManagerTask.web.dto.model.UserDto;
import com.example.ManagerTask.web.dto.validation.OnCreate;
import com.example.ManagerTask.web.mappers.UserMapper;
import com.example.ManagerTask.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Validated
public class AuthController {
    private final AuthService authService;

    private final UserService userService;

    private final UserMapper userMapper;

    @PostMapping("/login")
    public JwtResponse login(@Validated @RequestBody JwtRequest loginRequest){
        return authService.login(loginRequest);
    }

    @PostMapping("/register")
    private UserDto register(@Validated(OnCreate.class) @RequestBody UserDto userDto){
        User user = userMapper.toEntity(userDto);
        User createUser = userService.create(user);
        return userMapper.toDto(createUser);
    }

    @PostMapping("/refresh")
    public JwtResponse refresh(@RequestBody String refreshToken){
        return authService.refresh(refreshToken);
    }
}
