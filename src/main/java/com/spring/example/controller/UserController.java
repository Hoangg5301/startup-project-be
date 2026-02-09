package com.spring.example.controller;

import com.spring.example.dto.request.user.CreateUserRequest;
import com.spring.example.dto.request.user.UpdateUserRequest;
import com.spring.example.dto.response.user.CreateUserResponse;
import com.spring.example.dto.response.user.GetUserResponse;
import com.spring.example.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Tag(name = "User Management", description = "APIs for managing users")
public class UserController {

    private final UserService userService;

    @PostMapping
    @Operation(summary = "Create a new user", description = "Creates a new user in the system")
    public ResponseEntity<CreateUserResponse> createUser(@Valid @RequestBody CreateUserRequest request) {
        CreateUserResponse response = userService.createUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_USER')")
    @Operation(summary = "Get user by ID", description = "Retrieves a user by their unique identifier")
    public ResponseEntity<GetUserResponse> getUserById(@PathVariable UUID id) {
        GetUserResponse response = userService.getUserById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}/admin")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    @Operation(summary = "Get user by ID", description = "Retrieves a user by their unique identifier")
    public ResponseEntity<GetUserResponse> getUserByIdAdmin(@PathVariable UUID id) {
        GetUserResponse response = userService.getUserById(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update user", description = "Updates an existing user's information")
    public ResponseEntity<GetUserResponse> updateUser(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateUserRequest request) {
        GetUserResponse response = userService.updateUser(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete user", description = "Deletes a user from the system")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    @Operation(summary = "Get all users", description = "Retrieves a paginated list of all users")
    public ResponseEntity<Page<GetUserResponse>> getAllUsers(Pageable pageable) {
        Page<GetUserResponse> response = userService.getAllUsers(pageable);
        return ResponseEntity.ok(response);
    }
}
