package com.devops.backend.rewear.services;

import com.devops.backend.rewear.dtos.request.SaveUser;
import com.devops.backend.rewear.dtos.response.GetUserProfile;
import com.devops.backend.rewear.dtos.response.GetUser;
import com.devops.backend.rewear.entities.User;
import com.devops.backend.rewear.entities.enums.UserStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {
    Page<GetUser> getAllUsers(Pageable pageable);
    Page<GetUser> getActiveUsers(Pageable pageable);
    GetUser getById(Long id);
    GetUserProfile getMyProfile();
    GetUser getByUsername(String username);
    User getEntityByUsername(String username);
    GetUser getByEmail(String email);
    GetUser updateById(Long id, SaveUser saveUser);
    GetUser updateStatus(Long userId, UserStatus userStatus);
}
