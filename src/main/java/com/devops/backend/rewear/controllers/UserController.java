package com.devops.backend.rewear.controllers;

import com.devops.backend.rewear.dtos.response.GetUser;
import com.devops.backend.rewear.services.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<Page<GetUser>> getAllUsers(Pageable pageable) {
        Page<GetUser> getUserPage = userService.getUsers(pageable);
        return ResponseEntity.ok(getUserPage);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetUser> getUser(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getById(id));
    }

}
