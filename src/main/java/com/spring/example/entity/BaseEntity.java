package com.spring.example.entity;

import com.spring.example.auth.SecurityUtils;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@MappedSuperclass
@Getter
@Setter
public abstract class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public UUID id;
    public Instant createAt;
    public Instant updateAt;
    public String createBy;
    public String updateBy;

    @PrePersist
    public void prePersist() {
        if (createAt == null) {
            createAt = Instant.now();
            updateAt = createAt;
        }
        // TODO extract data from authentication
        UUID user = SecurityUtils.getCurrentUserLogin().orElse(null);
        createBy = user == null ? null : user.toString();
        updateBy = user == null ? null : user.toString();
    }

    @PreUpdate
    public void preUpdate() {
        updateAt = Instant.now();
        UUID user = SecurityUtils.getCurrentUserLogin().orElse(null);
        updateBy = user == null ? null : user.toString();
    }
}
