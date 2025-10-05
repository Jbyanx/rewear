package com.devops.backend.rewear.services;

import com.devops.backend.rewear.dtos.request.SaveUser;
import com.devops.backend.rewear.dtos.response.GetUserProfile;
import com.devops.backend.rewear.dtos.response.GetUser;
import com.devops.backend.rewear.entities.User;
import com.devops.backend.rewear.entities.enums.UserStatus;

import java.util.List;

public interface UserService {
    List<GetUser> getAllUsers();
    List<GetUser> getActiveUsers();
    GetUser getById(Long id);
    GetUserProfile getMyProfile();
    GetUser getByUsername(String username);
    User getEntityByUsername(String username);
    GetUser getByEmail(String email);
    GetUser updateById(Long id, SaveUser saveUser);
    GetUser updateStatus(Long userId, UserStatus userStatus);
}
