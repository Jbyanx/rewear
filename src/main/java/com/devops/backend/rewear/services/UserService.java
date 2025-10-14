package com.devops.backend.rewear.services;

import com.devops.backend.rewear.dtos.request.UpdateUser;
import com.devops.backend.rewear.dtos.response.GetUserProfile;
import com.devops.backend.rewear.entities.User;
import com.devops.backend.rewear.entities.enums.UserStatus;
import jakarta.validation.Valid;

import java.util.List;

public interface UserService {
    List<GetUserProfile> getAllUsers();
    List<GetUserProfile> getActiveUsers();
    GetUserProfile getById(Long id);
    GetUserProfile getMyProfile();
    GetUserProfile getByUsername(String username);
    User getEntityByUsername(String username);
    GetUserProfile getByEmail(String email);
    GetUserProfile updateById(Long id, @Valid UpdateUser updateUser);
    GetUserProfile updateStatus(Long userId, UserStatus userStatus);
}
