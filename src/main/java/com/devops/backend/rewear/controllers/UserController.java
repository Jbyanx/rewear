package com.devops.backend.rewear.controllers;

import com.devops.backend.rewear.dtos.response.GetUser;
import com.devops.backend.rewear.dtos.response.GetUserProfile;
import com.devops.backend.rewear.entities.enums.UserStatus;
import com.devops.backend.rewear.services.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<Page<GetUser>> getActiveUsers(Pageable pageable) {
        Page<GetUser> getUserPage = userService.getActiveUsers(pageable);
        return ResponseEntity.ok(getUserPage);
    }

    @GetMapping("/all")
    public ResponseEntity<Page<GetUser>> getAllUsers(Pageable pageable) {
        Page<GetUser> getUserPage = userService.getAllUsers(pageable);
        return ResponseEntity.ok(getUserPage);
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
}
