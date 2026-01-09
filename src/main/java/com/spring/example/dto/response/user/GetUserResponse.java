package com.spring.example.dto.response.user;

import com.spring.example.common.role.SystemRole;
import com.spring.example.common.user.Gender;
import lombok.Data;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;

@Data
public class GetUserResponse {

    private UUID id;

    private String name;

    private String phone;

    private String email;

    private boolean active;

    private Set<SystemRole> roles;

    private Gender gender;

    private String imageUrl;
}
