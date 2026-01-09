package com.spring.example.service.Impl;

import com.spring.example.auth.JwtUtils;
import com.spring.example.dto.request.auth.LoginRequest;
import com.spring.example.dto.response.auth.LoginResponse;
import com.spring.example.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final JwtUtils jwtUtils;

    private PasswordEncoder passwordEncoder;

    @Override
    public LoginResponse login(LoginRequest request) {

        return null;
    }
}
