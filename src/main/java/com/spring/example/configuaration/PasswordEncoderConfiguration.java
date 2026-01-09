package com.spring.example.configuaration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PasswordEncoderConfiguration {

    @Bean
    public PasswordEncoderConfiguration passwordEncoder() {
        return new PasswordEncoderConfiguration();
    }
}
