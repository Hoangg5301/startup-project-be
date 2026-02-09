package com.spring.example.auth;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationFilter extends OncePerRequestFilter {

    private final AuthenticationManager authenticationManager;

    private final JwtUtils jwtUtils;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
        }

        if (token != null) {
            Authentication auth = authenticationManager.authenticate(new JwtAuthenticationToken(token));
            if(auth.isAuthenticated()) {
                SecurityContextHolder.getContext().setAuthentication(auth);
            } else {
                logger.warn("Invalid JWT token");
                unauthenticated(response);
                return;
            }
        }

        filterChain.doFilter(request, response);
    }

    private void unauthenticated(HttpServletResponse response) throws IOException {

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
        SecurityContextHolder.clearContext();
    }
}
