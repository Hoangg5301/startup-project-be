package com.spring.example.auth;

import io.jsonwebtoken.Claims;
import lombok.*;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
@Getter
@Setter
public class CustomAuthenticationJwtProvider implements AuthenticationProvider {

    private final JwtUtils jwtUtils;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        JwtAuthenticationToken jwtAuthenticationToken = (JwtAuthenticationToken) authentication;
        Claims claims = jwtUtils.extractClaim(jwtAuthenticationToken.getToken(), new Function<Claims, Claims>() {
            @Override
            public Claims apply(Claims o) {
                return o;
            }
        });

        @SuppressWarnings("unchecked")
        List<String> roles = (List<String>) claims.get("roles");
        Collection<SimpleGrantedAuthority> simpleGrantedAuthorities = roles.stream()
                .map(SimpleGrantedAuthority::new)
                .toList();

        return new JwtAuthenticationToken(claims, simpleGrantedAuthorities);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(JwtAuthenticationToken.class);
    }
}
