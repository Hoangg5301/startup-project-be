package com.spring.example.service;

import com.spring.example.dto.request.user.CreateUserRequest;
import com.spring.example.dto.request.user.UpdateUserRequest;
import com.spring.example.dto.response.user.CreateUserResponse;
import com.spring.example.dto.response.user.GetUserResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface UserService {

    CreateUserResponse createUser(CreateUserRequest request);

    GetUserResponse getUserById(UUID id);

    GetUserResponse updateUser(UUID id, UpdateUserRequest request);

    void deleteUser(UUID id);

    Page<GetUserResponse> getAllUsers(Pageable pageable);
}
