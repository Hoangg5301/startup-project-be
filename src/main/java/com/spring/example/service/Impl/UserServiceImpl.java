package com.spring.example.service.Impl;

import com.spring.example.common.exception.BusinessException;
import com.spring.example.dto.request.user.CreateUserRequest;
import com.spring.example.dto.request.user.UpdateUserRequest;
import com.spring.example.dto.response.user.CreateUserResponse;
import com.spring.example.dto.response.user.GetUserResponse;
import com.spring.example.entity.UserEntity;
import com.spring.example.mapper.UserMapper;
import com.spring.example.repository.UserRepository;
import com.spring.example.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Value(value = "${default.password}")
    private String defaultPassword;

    @Override
    public CreateUserResponse createUser(CreateUserRequest request) {
        // Check if email already exists
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new BusinessException("Email already exists");
        }
        if (userRepository.findByUserName(request.getUserName()).isPresent()) {
            throw new BusinessException("UserName already exists");
        }

        UserEntity userEntity = userMapper.toEntity(request);
        userEntity.setPassword(passwordEncoder.encode(defaultPassword));

        UserEntity savedUser = userRepository.save(userEntity);
        return userMapper.toCreateResponse(savedUser);
    }

    @Override
    @Transactional(readOnly = true)
    public GetUserResponse getUserById(UUID id) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        return userMapper.toGetResponse(user);
    }

    @Override
    public GetUserResponse updateUser(UUID id, UpdateUserRequest request) {
        UserEntity existingUser = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));

        // Check if email is being updated and if it already exists
        if (request.getEmail() != null && !request.getEmail().equals(existingUser.getEmail())) {
            if (userRepository.findByEmail(request.getEmail()).isPresent()) {
                throw new RuntimeException("Email already exists");
            }
        }

        userMapper.updateEntityFromDto(request, existingUser);
        UserEntity updatedUser = userRepository.save(existingUser);
        return userMapper.toGetResponse(updatedUser);
    }

    @Override
    public void deleteUser(UUID id) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        userRepository.delete(user);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<GetUserResponse> getAllUsers(Pageable pageable) {
        Page<UserEntity> users = userRepository.findAll(pageable);
        return users.map(userMapper::toGetResponse);
    }
}
