package com.devops.backend.rewear.controllers;

import com.devops.backend.rewear.dtos.request.SaveUser;
import com.devops.backend.rewear.dtos.response.GetUser;
import com.devops.backend.rewear.dtos.response.GetUserProfile;
import com.devops.backend.rewear.entities.enums.UserStatus;
import com.devops.backend.rewear.services.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<GetUser>> getActiveUsers() {
        return ResponseEntity.ok(userService.getActiveUsers());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/all")
    public ResponseEntity<List<GetUser>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetUser> getUser(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getById(id));
    }

    @GetMapping("/me")
    public ResponseEntity<GetUserProfile> getMyProfile() {
        return ResponseEntity.ok(userService.getMyProfile());
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<GetUser> updateStatus(
            @PathVariable Long id,
            @RequestParam UserStatus status
    ) {
        GetUser updatedUser = userService.updateStatus(id, status);
        return ResponseEntity.ok(updatedUser);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GetUser> updateUser(
            @PathVariable Long id,
            @Valid @RequestBody SaveUser saveUser
    ) {
        return ResponseEntity.ok(userService.updateById(id, saveUser));
    }
}
