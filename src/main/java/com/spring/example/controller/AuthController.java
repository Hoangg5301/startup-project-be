package com.spring.example.controller;

import com.spring.example.common.response.ApiResponse;
import com.spring.example.dto.request.auth.LoginRequest;
import com.spring.example.dto.response.auth.LoginResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(LoginRequest request) {

        log.info("login");

        return null;
    }
}
