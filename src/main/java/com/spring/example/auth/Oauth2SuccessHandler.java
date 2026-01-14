package com.spring.example.auth;

import io.jsonwebtoken.Claims;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class Oauth2SuccessHandler implements AuthenticationSuccessHandler {

    private final JwtUtils jwtUtils;
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        OAuth2User user = (OAuth2User) authentication.getPrincipal();

        String email = user.getAttribute("email");

        Collection<String> grantedAuthorities = authentication.getAuthorities().stream()
                .map(
                      GrantedAuthority::getAuthority
                ).toList();
        Map<String, Object> claims = new HashMap<>();
        claims.put("roles", grantedAuthorities);

        String token = jwtUtils.generateToken(
            claims,
                email
        );

        response.setContentType("application/json");
        response.getWriter().write("""
            {
              "accessToken": "%s"
            }
        """.formatted(token));
    }
}
