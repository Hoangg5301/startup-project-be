package com.spring.example.auth.user_password;

import com.spring.example.auth.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomUserNamePasswordAuthenticationProvider implements AuthenticationProvider {

    private final JwtUtils jwtUtils;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String token = (String) authentication.getCredentials();
        return new UsernamePasswordAuthenticationToken(
                jwtUtils.extractUsername(token),
                null,
                jwtUtils.extractRole(token)
        );
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return false;
    }
}
