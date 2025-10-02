package com.devops.backend.rewear.services.impl;

import com.devops.backend.rewear.dtos.request.LoginRequest;
import com.devops.backend.rewear.dtos.request.SaveUser;
import com.devops.backend.rewear.dtos.response.GetUserProfile;
import com.devops.backend.rewear.dtos.response.LoginResponse;
import com.devops.backend.rewear.entities.User;
import com.devops.backend.rewear.entities.enums.Role;
import com.devops.backend.rewear.exceptions.*;
import com.devops.backend.rewear.mappers.UserMapper;
import com.devops.backend.rewear.repositories.UserRepository;
import com.devops.backend.rewear.services.UserService;
import com.devops.backend.rewear.util.PasswordValidator;
import jakarta.validation.Valid;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class AuthService {
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserService userService;

    public AuthService(UserMapper userMapper, PasswordEncoder passwordEncoder, PasswordEncoder passwordEncoder1, UserRepository userRepository, AuthenticationManager authenticationManager, JwtService jwtService, UserService userService) {
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder1;
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.userService = userService;
    }

    @Transactional
    public GetUserProfile register(SaveUser saveUser) {
        // Validar que no existan email/username duplicados
        if(userRepository.existsByEmail(saveUser.email())){
            throw new EmailAlreadyExistsException("El email ya está registrado");
        }

        if(userRepository.existsByUsername(saveUser.username())){
            throw new UsernameAlreadyExistsException("El username ya está en uso");
        }
        if(!saveUser.password().equals(saveUser.repeatPassword())){
            throw new InvalidPasswordException("Las contraseñas ingresadas son diferentes");
        }

        String password = saveUser.password();
        if(!PasswordValidator.isValid(password)){
            throw new InvalidPasswordException(PasswordValidator.getValidationMessage(password));
        }

        // ✅ Mapear una sola vez y configurar todo
        User user = userMapper.toEntity(saveUser);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole(Role.USER);
        user.setRating(BigDecimal.valueOf(0.0));
        user.setTotalRatings(0);
        user.setActive(true);

        // ✅ Guardar directamente
        User savedUser = userRepository.save(user);
        return userMapper.toGetUserProfile(savedUser);
    }

    public LoginResponse login(@Valid LoginRequest loginRequest) {
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                loginRequest.username(),
                loginRequest.password()
        );
        authenticationManager.authenticate(authentication);

        User user = userRepository.getReferenceByUsername(loginRequest.username())
                .orElseThrow(() -> new UserNotFoundException("El usuario " + loginRequest.username() + " no existe en BDD"));

        Map<String, Object> claims = generateExtraClaims(user);

        String accessToken = jwtService.generateAccessToken(user, claims);
        String refreshToken = jwtService.generateRefreshToken(user, claims);

        GetUserProfile userProfile = userMapper.toGetUserProfile(user);
        return new LoginResponse(
                accessToken,
                refreshToken,
                userProfile
        );
    }

    public LoginResponse refreshToken(String refreshToken) {
        Date now = new Date();

        if (!jwtService.isNotExpired(refreshToken, now)) {
            throw new InvalidTokenException("El refresh token ha expirado");
        }

        String username = jwtService.extractUsername(refreshToken);
        User entity = userService.getEntityByUsername(username);

        String newAccessToken = jwtService.generateAccessToken(entity, generateExtraClaims(entity));

        return new LoginResponse(
                newAccessToken,
                refreshToken,
                userMapper.toGetUserProfile(entity)
        );
    }

    private Map<String, Object> generateExtraClaims(User user) {
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("name", user.getFirstName());
        extraClaims.put("role", user.getRole().name());
        extraClaims.put("currentUserId", user.getId());
        return extraClaims;
    }
}
