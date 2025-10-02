package com.devops.backend.rewear.services.impl;

import com.devops.backend.rewear.dtos.request.SaveUser;
import com.devops.backend.rewear.dtos.response.GetUser;
import com.devops.backend.rewear.dtos.response.GetUserProfile;
import com.devops.backend.rewear.entities.User;
import com.devops.backend.rewear.exceptions.UserNotFoundException;
import com.devops.backend.rewear.mappers.UserMapper;
import com.devops.backend.rewear.repositories.UserRepository;
import com.devops.backend.rewear.services.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final CurrentUserService currentUserService;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper, CurrentUserService currentUserService) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.currentUserService = currentUserService;
    }

    @Override
    public Page<GetUser> getActiveUsers(Pageable pageable) {
        return userRepository.findByIsActiveTrue(pageable)
                .map(userMapper::toGetUser);
    }

    @Override
    public Page<GetUser> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable)
                .map(userMapper::toGetUser);
    }

    @Override
    public GetUser getById(Long id) {
        return userMapper.toGetUser(
                userRepository.findById(id)
                        .orElseThrow(() -> new UserNotFoundException("Usuario no encontrado en BDD"))
        );
    }

    @Override
    public GetUserProfile getMyProfile() {
        return userMapper.toGetUserProfile(currentUserService.getAuthenticatedUser());
    }

    @Override
    public GetUser getByUsername(String username) {
        return userRepository.findByUsername(username)
                .map(userMapper::toGetUser)
                .orElseThrow(() -> new  UserNotFoundException("Usuario con username "+username+" no encontrado en BDD"));
    }

    @Override
    public User getEntityByUsername(String username) {
        return userRepository.getByUsername(username)
                .orElseThrow(() -> new  UserNotFoundException("Usuario con username "+username+" no encontrado en BDD"));
    }

    @Override
    public GetUser getByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(userMapper::toGetUser)
                .orElseThrow(() -> new UserNotFoundException("usuario con email "+email+" no existe"));
    }

    @Transactional
    @Override
    public GetUser updateById(Long id, SaveUser saveUser) {
        if(!userRepository.existsById(id)) throw new UserNotFoundException("usuario con id "+id+" no encontrado en BDD");
        User oldUser = userRepository.getReferenceById(id);

        if (saveUser.firstName() != null) oldUser.setFirstName(saveUser.firstName());
        if (saveUser.lastName() != null) oldUser.setLastName(saveUser.lastName());
        if (saveUser.phoneNumber() != null) oldUser.setPhoneNumber(saveUser.phoneNumber());
        if (saveUser.email() != null) oldUser.setEmail(saveUser.email());
        if (saveUser.username() != null) oldUser.setUsername(saveUser.username());
        if (saveUser.address() != null) oldUser.setAddress(saveUser.address());
        if (saveUser.city() != null) oldUser.setCity(saveUser.city());
        if (saveUser.country() != null) oldUser.setCountry(saveUser.country());
        if (saveUser.birthDate() != null) oldUser.setBirthDate(saveUser.birthDate());
        if (saveUser.genre() != null) oldUser.setGenre(saveUser.genre());
        if (saveUser.documentType() != null) oldUser.setDocumentType(saveUser.documentType());
        if (saveUser.documentNumber() != null) oldUser.setDocumentNumber(saveUser.documentNumber());
        if (saveUser.profileImageUrl() != null) oldUser.setProfileImageUrl(saveUser.profileImageUrl());

        return userMapper.toGetUser(userRepository.save(oldUser));
    }

    @Override
    public GetUser desactivate(Long id) {
        return userRepository.findById(id)
                .map(u -> {
                    u.setActive(false); //desactivar
                    return userMapper.toGetUser(u);
                })
                .orElseThrow(() -> new UserNotFoundException("Error al desactivar el usuario en BDD, no existe"));
    }
}
