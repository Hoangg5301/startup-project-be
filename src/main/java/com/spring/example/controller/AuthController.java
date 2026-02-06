package com.spring.example.controller;

import com.spring.example.auth.JwtUtils;
import com.spring.example.auth.user_password.CustomAuthentication;
import com.spring.example.auth.user_password.CustomUserDetails;
import com.spring.example.common.response.ApiResponse;
import com.spring.example.dto.request.auth.LoginRequest;
import com.spring.example.dto.response.auth.LoginResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.security.auth.login.AccountLockedException;

@Slf4j
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "APIs for user authentication")
public class AuthController {

    private final AuthenticationManager authenticationManager;

    private final JwtUtils jwtUtils;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(@RequestBody LoginRequest request) throws AccountLockedException {

        Authentication authentication = authenticationManager.authenticate(
                new CustomAuthentication(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        if(!customUserDetails.getActive()){
            throw new AccountLockedException("Account is locked");
        }
        return ResponseEntity.ok(ApiResponse.success(jwtUtils.generateToken(customUserDetails)));

    }

    @PostMapping("/oauth2/google")
    public void login(HttpServletResponse response) {

        return;
    }
}
