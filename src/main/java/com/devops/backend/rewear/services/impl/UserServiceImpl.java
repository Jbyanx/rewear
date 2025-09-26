package com.devops.backend.rewear.services.impl;

import com.devops.backend.rewear.dtos.request.SaveUser;
import com.devops.backend.rewear.dtos.response.GetUser;
import com.devops.backend.rewear.dtos.response.GetUserProfile;
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

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
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
    public GetUserProfile getMyProfile(Long id) {
        return null;
    }

    @Override
    public GetUser getByUsername(String username) {
        return null;
    }

    @Override
    public GetUser getByEmail(String email) {
        return null;
    }

    @Override
    public GetUserProfile save(SaveUser saveUser) {
        return userMapper.toGetUserProfile(
                userRepository.save(userMapper.toEntity(saveUser))
        );
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
