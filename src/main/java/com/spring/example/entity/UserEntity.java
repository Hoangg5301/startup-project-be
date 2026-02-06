package com.spring.example.entity;

import com.spring.example.common.role.SystemRole;
import com.spring.example.common.user.Gender;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity extends BaseEntity {

    private String name;

    private String userName;

    private String phone;

    private String email;

    private boolean active = true;

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @CollectionTable(
            name = "user_roles_entity",
            joinColumns = @JoinColumn(name = "user_id")
    )
    @Column(name = "role")
    private Set<SystemRole> roles;

    private String password;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(name = "image_url")
    private String imageUrl;
}
