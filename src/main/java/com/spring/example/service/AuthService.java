package com.spring.example.service;

import com.spring.example.dto.request.auth.LoginRequest;
import com.spring.example.dto.response.auth.LoginResponse;

public interface AuthService {

    LoginResponse login(LoginRequest request);
}
