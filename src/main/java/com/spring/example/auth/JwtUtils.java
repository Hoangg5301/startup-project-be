package com.spring.example.auth;

import com.spring.example.auth.user_password.CustomUserDetails;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;


@Slf4j
@Component
public class JwtUtils {

    @Value("${jwt.private-key-path}")
    private String privateKeyPath;

    @Value("${jwt.public-key-path}")
    private String publicKeyPath;

    @Value("${jwt.expiration-time}")
    private long expirationTime;

    public String generateToken(CustomUserDetails userDetails) {

        Map<String, Object> claims = new HashMap<>();
        claims.put("user_name", userDetails.getUsername());
        claims.put("email", userDetails.getEmail());
        claims.put("roles", userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList());
        return generateToken(claims, userDetails.getUsername());
    }

    public String generateToken(Map<String, Object> extraClaims, String subject) {
        Date now = new Date();
        Date expiry = new Date(now.getTime() + expirationTime);

        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(subject)
                .setIssuedAt(now)
                .setExpiration(expiry)
                .signWith(RsaKeyLoader.loadPrivateKey(privateKeyPath), SignatureAlgorithm.RS256)
                .compact();
    }


    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(RsaKeyLoader.loadPublicKey(publicKeyPath))
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public <R> R extractClaim(String token, Function<Claims, R> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public String extractUsername(String token) {
        return extractClaim(token, new Function<Claims, String>() {
            @Override
            public String apply(Claims claims) {
                return claims.getSubject();
            }
        });
    }


    public String extractRole(String token) {
        return extractClaim(token, new Function<Claims, String>() {
            @Override
            public String apply(Claims claims) {
                return (String) claims.get("roles");
            }
        });
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private boolean isTokenExpired(String token) {
        Date exp = extractExpiration(token);
        return exp.before(new Date());
    }


    public boolean validateToken(String token, UserDetails userDetails) {
        try {
            final String username = extractUsername(token);
            return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
        } catch (JwtException | IllegalArgumentException e) {
            log.error("Token validation failed for user {}: {}", userDetails.getUsername(), e.getMessage(), e);
            return false;
        }
    }

    public ValidationResult validateTokenWithReason(String token) {
        try {
            extractAllClaims(token);
            return ValidationResult.valid();
        } catch (ExpiredJwtException e) {
            log.error("JWT token has expired: {}", e.getMessage());
            return ValidationResult.invalid("TOKEN_EXPIRED");
        } catch (MalformedJwtException e) {
            log.error("JWT token is malformed: {}", e.getMessage());
            return ValidationResult.invalid("MALFORMED_TOKEN");
        } catch (UnsupportedJwtException e) {
            log.error("JWT token format is not supported: {}", e.getMessage());
            return ValidationResult.invalid("UNSUPPORTED_TOKEN");
        } catch (IllegalArgumentException e) {
            log.error("JWT token validation failed due to illegal argument: {}", e.getMessage());
            return ValidationResult.invalid("ILLEGAL_ARGUMENT");
        } catch (JwtException e) {
            log.error("JWT token validation failed with general JWT error: {}", e.getMessage(), e);
            return ValidationResult.invalid("JWT_ERROR");
        } catch (Exception e) {
            log.error("JWT token validation failed: {}", e.getMessage());
            return ValidationResult.invalid("INVALID_TOKEN");
        }
    }

    @Getter
    public static class ValidationResult {

        private final boolean valid;
        private final String reason;

        private ValidationResult(boolean valid, String reason) {
            this.valid = valid;
            this.reason = reason;
        }

        public static ValidationResult valid() {
            return new ValidationResult(true, null);
        }

        public static ValidationResult invalid(String reason) {
            return new ValidationResult(false, reason);
        }

    }
}
