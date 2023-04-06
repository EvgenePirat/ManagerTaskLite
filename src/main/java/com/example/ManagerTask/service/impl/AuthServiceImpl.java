package com.example.ManagerTask.service.impl;

import com.example.ManagerTask.service.AuthService;
import com.example.ManagerTask.web.dto.auth.JwtRequest;
import com.example.ManagerTask.web.dto.auth.JwtResponse;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {
    @Override
    public JwtResponse login(JwtRequest loginRequest) {
        return null;
    }

    @Override
    public JwtResponse refresh(String refreshToken) {
        return null;
    }
}
