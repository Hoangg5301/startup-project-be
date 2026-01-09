package com.spring.example.auth;

import com.spring.example.common.role.SystemRole;
import com.spring.example.entity.UserEntity;
import com.spring.example.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        UserEntity userEntity = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(email));

        String [] roles = userEntity.getRoles().stream()
                .map(SystemRole::getRoleName)
                .toArray(String[]::new);
        return User.withUsername(userEntity.getEmail())
                .password(userEntity.getPassword())
                .roles(roles).build();
    }
}
