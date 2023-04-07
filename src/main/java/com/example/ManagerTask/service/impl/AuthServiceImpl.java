package com.example.ManagerTask.service.impl;

import com.example.ManagerTask.domain.User;
import com.example.ManagerTask.security.jwt.JwtTokenProvider;
import com.example.ManagerTask.service.AuthService;
import com.example.ManagerTask.service.UserService;
import com.example.ManagerTask.web.dto.auth.JwtRequest;
import com.example.ManagerTask.web.dto.auth.JwtResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;
    @Override
    public JwtResponse login(JwtRequest loginRequest) {
        JwtResponse jwtResponse = new JwtResponse();
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),loginRequest.getPassword()));
        User user = userService.getByUsername(loginRequest.getUsername());
        System.out.println(user.getId());
        jwtResponse.setId(user.getId());
        jwtResponse.setUsername(user.getUsername());
        jwtResponse.setAccessToken(jwtTokenProvider.createAccessToken(user.getId(),user.getUsername(),user.getRoles()));
        jwtResponse.setRefreshToken(jwtTokenProvider.createRefreshToken(user.getId(),user.getUsername()));
        System.out.println(jwtResponse.getAccessToken());
        return jwtResponse;
    }

    @Override
    public JwtResponse refresh(String refreshToken) {
        return jwtTokenProvider.refreshUserToken(refreshToken);
    }
}
