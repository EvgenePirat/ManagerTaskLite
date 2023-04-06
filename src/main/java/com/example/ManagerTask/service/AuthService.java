package com.example.ManagerTask.service;

import com.example.ManagerTask.web.dto.auth.JwtRequest;
import com.example.ManagerTask.web.dto.auth.JwtResponse;

public interface AuthService {
    JwtResponse login(JwtRequest loginRequest);

    JwtResponse refresh(String refreshToken);

}
