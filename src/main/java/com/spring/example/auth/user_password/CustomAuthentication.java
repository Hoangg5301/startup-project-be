package com.spring.example.auth.user_password;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public class CustomAuthentication extends UsernamePasswordAuthenticationToken {

    public CustomAuthentication(Object principal, Object credentials) {
        super(principal, credentials);
    }
}
