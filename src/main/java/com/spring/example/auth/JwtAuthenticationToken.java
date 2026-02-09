package com.spring.example.auth;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Getter
@Setter
public class JwtAuthenticationToken extends AbstractAuthenticationToken {

    private String token;

    private Object principal;

    public JwtAuthenticationToken(String token) {
        super(null);
        this.token = token;
        this.principal = null;
        this.setAuthenticated(false);
    }

    public JwtAuthenticationToken(Object principal, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal;
        super.setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return token;
    }

    @Override
    public Object getPrincipal() {
        return principal;
    }
}
