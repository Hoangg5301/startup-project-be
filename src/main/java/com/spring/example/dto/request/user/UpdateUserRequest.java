package com.spring.example.dto.request.user;

import com.spring.example.common.role.SystemRole;
import com.spring.example.common.user.Gender;
import jakarta.validation.constraints.Email;
import lombok.Data;

import java.util.Set;

@Data
public class UpdateUserRequest {
    
    private String name;
    
    private String phone;
    
    @Email(message = "Email should be valid")
    private String email;
    
    private Gender gender;
    
    private String imageUrl;
    
    private Boolean active;
    
    private Set<SystemRole> roles;
}
