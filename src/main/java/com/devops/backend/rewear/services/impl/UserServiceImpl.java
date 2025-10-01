package com.devops.backend.rewear.services.impl;

import com.devops.backend.rewear.dtos.request.SaveUser;
import com.devops.backend.rewear.dtos.response.GetUser;
import com.devops.backend.rewear.dtos.response.GetUserProfile;
import com.devops.backend.rewear.entities.User;
import com.devops.backend.rewear.exceptions.UserNotAuthenticatedException;
import com.devops.backend.rewear.exceptions.UserNotFoundException;
import com.devops.backend.rewear.mappers.UserMapper;
import com.devops.backend.rewear.repositories.UserRepository;
import com.devops.backend.rewear.services.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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
    public Page<GetUser> getUsers(Pageable pageable) {
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
        return null;
    }

    @Override
    public GetUser updateById(Long id, SaveUser saveUser) {
        return null;
    }

    @Override
    public GetUser desactivate(Long id) {
        return userRepository.findById(id)
                .map(userMapper::toGetUser)
                .orElseThrow(() -> new UserNotFoundException("Error al desactivar el usuario en BDD, no existe"));
    }
}
