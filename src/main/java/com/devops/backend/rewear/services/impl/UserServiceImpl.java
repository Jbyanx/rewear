package com.devops.backend.rewear.services.impl;

import com.devops.backend.rewear.dtos.request.UpdateUser;
import com.devops.backend.rewear.dtos.response.GetUserProfile;
import com.devops.backend.rewear.entities.User;
import com.devops.backend.rewear.entities.enums.Role;
import com.devops.backend.rewear.entities.enums.UserStatus;
import com.devops.backend.rewear.exceptions.EmailAlreadyExistsException;
import com.devops.backend.rewear.exceptions.PermissionDeniedException;
import com.devops.backend.rewear.exceptions.UserNotFoundException;
import com.devops.backend.rewear.exceptions.UsernameAlreadyExistsException;
import com.devops.backend.rewear.mappers.UserMapper;
import com.devops.backend.rewear.repositories.UserRepository;
import com.devops.backend.rewear.services.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
    public List<GetUserProfile> getActiveUsers() {
        return userRepository.findByIsActiveTrue()
                .stream()
                .map(userMapper::toGetUserProfile).toList();
    }

    @Override
    public List<GetUserProfile> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toGetUserProfile).toList();
    }

    @Override
    public GetUserProfile getById(Long id) {
        return userMapper.toGetUserProfile(
                userRepository.findById(id)
                        .orElseThrow(() -> new UserNotFoundException("Usuario no encontrado en BDD"))
        );
    }

    @Override
    public GetUserProfile getMyProfile() {
        User authenticatedUser = currentUserService.getAuthenticatedUser(); // ussuario del contexto de seguridad

        //usuario del contexto de hibernate
        User user = userRepository.findById(authenticatedUser.getId())
                .orElseThrow(() -> new UserNotFoundException("Usuario no encontrado en BDD"));

        int size = user.getWears().size(); //cargamos los wears para evitar lazy loading
        return userMapper.toGetUserProfile(user);
    }

    @Override
    public GetUserProfile getByUsername(String username) {
        return userRepository.findByUsername(username)
                .map(userMapper::toGetUserProfile)
                .orElseThrow(() -> new  UserNotFoundException("Usuario con username "+username+" no encontrado en BDD"));
    }

    @Override
    public User getEntityByUsername(String username) {
        return userRepository.getByUsername(username)
                .orElseThrow(() -> new  UserNotFoundException("Usuario con username "+username+" no encontrado en BDD"));
    }

    @Override
    public GetUserProfile getByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(userMapper::toGetUserProfile)
                .orElseThrow(() -> new UserNotFoundException("usuario con email "+email+" no existe"));
    }

    @Transactional
    @Override
    public GetUserProfile updateById(Long id, @Valid UpdateUser updateUser) {
        User currentUser = currentUserService.getAuthenticatedUser();

        if(!userRepository.existsById(id)) throw new UserNotFoundException("usuario con id "+id+" no encontrado en BDD");

        if(!(currentUser.getRole().equals(Role.ADMIN) || currentUser.getId().equals(id))) {
            throw new PermissionDeniedException("Vaya, parece que no tienes permisos para solicitar este recurso");
        }

        User oldUser = userRepository.getReferenceById(id);

        if (updateUser.firstName() != null) oldUser.setFirstName(updateUser.firstName());
        if (updateUser.lastName() != null) oldUser.setLastName(updateUser.lastName());
        if (updateUser.phoneNumber() != null) oldUser.setPhoneNumber(updateUser.phoneNumber());
        if (updateUser.email() != null){
            if(userRepository.existsByEmail(updateUser.email())){
                throw new EmailAlreadyExistsException("Error al actualizar el usuario, el email "+updateUser.email()+" ya está en uso");
            }
            oldUser.setEmail(updateUser.email());
        }
        if (updateUser.username() != null){
            if(userRepository.existsByUsername(updateUser.username())){
                throw new UsernameAlreadyExistsException("Error al actualizar el usuario, el username "+updateUser.username()+" ya está en uso");
            }
            oldUser.setUsername(updateUser.username());
        }
        if (updateUser.address() != null) oldUser.setAddress(updateUser.address());
        if (updateUser.city() != null) oldUser.setCity(updateUser.city());
        if (updateUser.country() != null) oldUser.setCountry(updateUser.country());
        if (updateUser.birthDate() != null) oldUser.setBirthDate(updateUser.birthDate());
        if (updateUser.genre() != null) oldUser.setGenre(updateUser.genre());
        if (updateUser.documentType() != null) oldUser.setDocumentType(updateUser.documentType());
        if (updateUser.documentNumber() != null) oldUser.setDocumentNumber(updateUser.documentNumber());
        if (updateUser.profileImageUrl() != null) oldUser.setProfileImageUrl(updateUser.profileImageUrl());

        return userMapper.toGetUserProfile(userRepository.save(oldUser));
    }

    @Transactional
    @Override
    public GetUserProfile updateStatus(Long userId, UserStatus userStatus) {
        User currentUser = currentUserService.getAuthenticatedUser();

        boolean isAdmin = currentUser.getRole().equals(Role.ADMIN);
        boolean isOwner = currentUser.getId().equals(userId);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("Usuario no encontrado"));

        // Caso: el propio usuario quiere desactivar su cuenta
        // asi controlamos que el usuario no pueda reactivarse
        if (isOwner && userStatus == UserStatus.INACTIVE) {
            user.setActive(false);
        }
        // Caso: admin puede activar o desactivar cualquier cuenta
        else if (isAdmin) {
            user.setActive(userStatus == UserStatus.ACTIVE);
        }
        else {
            throw new PermissionDeniedException("No tienes permisos para esta acción");
        }

        return userMapper.toGetUserProfile(userRepository.save(user));
    }

}
