package com.spring.example.auth.user_password;

import com.spring.example.common.role.SystemRole;
import com.spring.example.entity.UserEntity;
import com.spring.example.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

        UserEntity userEntity = userRepository.findByUserName(userName)
                .orElseThrow(() -> new UsernameNotFoundException(userName));

        List<SimpleGrantedAuthority> roles = userEntity.getRoles().stream()
                .map(systemRole -> {
                    return new SimpleGrantedAuthority("ROLE_" + systemRole.getRoleName());
                })
                .toList();

        return CustomUserDetails.builder()
                .id(userEntity.getId())
                .username(userEntity.getUserName())
                .email(userEntity.getEmail())
                .active(userEntity.isActive())
                .password(userEntity.getPassword())
                .roles(roles)
                .build();
    }
}
