package com.devops.backend.rewear.controllers;

import com.devops.backend.rewear.dtos.request.LoginRequest;
import com.devops.backend.rewear.dtos.request.RefreshToken;
import com.devops.backend.rewear.dtos.request.SaveUser;
import com.devops.backend.rewear.dtos.response.GetUserProfile;
import com.devops.backend.rewear.dtos.response.LoginResponse;
import com.devops.backend.rewear.mappers.UserMapper;
import com.devops.backend.rewear.services.UserService;
import com.devops.backend.rewear.services.impl.AuthService;
import com.devops.backend.rewear.services.impl.JwtService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;
    private final JwtService jwtService;
    private final UserService userService;
    private final UserMapper userMapper;

    public AuthController(AuthService authService, JwtService jwtService, UserService userService, UserMapper userMapper) {
        this.authService = authService;
        this.jwtService = jwtService;
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @PostMapping("/register")
    public ResponseEntity<GetUserProfile> register(@Valid @RequestBody SaveUser saveUser) {
        return ResponseEntity.ok(authService.register(saveUser));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(authService.login(loginRequest));
    }

    @PostMapping("/refresh")
    public ResponseEntity<LoginResponse> refresh(@RequestBody RefreshToken refreshToken) {
        LoginResponse response = authService.refreshToken(refreshToken.refreshToken());
        return ResponseEntity.ok(response);
    }

}
