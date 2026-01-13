package com.spring.example.auth;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.nio.file.attribute.UserPrincipal;
import java.util.Collection;

public class CustomAuthentication extends AbstractAuthenticationToken {

    private final UserPrincipal principal;

    public CustomAuthentication(Collection<? extends GrantedAuthority> authorities, UserPrincipal principal) {
        super(authorities);
        this.principal = principal;
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return null;
    }
}
