package com.devops.backend.rewear.services;

import com.devops.backend.rewear.dtos.request.SaveUser;
import com.devops.backend.rewear.dtos.response.GetUserProfile;
import com.devops.backend.rewear.dtos.response.GetUser;
import com.devops.backend.rewear.entities.User;
import com.devops.backend.rewear.entities.enums.UserStatus;

import java.util.List;

public interface UserService {
    List<GetUserProfile> getAllUsers();
    List<GetUserProfile> getActiveUsers();
    GetUserProfile getById(Long id);
    GetUserProfile getMyProfile();
    GetUserProfile getByUsername(String username);
    User getEntityByUsername(String username);
    GetUserProfile getByEmail(String email);
    GetUserProfile updateById(Long id, SaveUser saveUser);
    GetUserProfile updateStatus(Long userId, UserStatus userStatus);
}
