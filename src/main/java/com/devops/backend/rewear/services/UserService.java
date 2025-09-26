package com.devops.backend.rewear.services;

import com.devops.backend.rewear.dtos.request.SaveUser;
import com.devops.backend.rewear.dtos.response.GetMyUserProfile;
import com.devops.backend.rewear.dtos.response.GetUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {
    Page<GetUser>  getUsers(Pageable pageable);
    GetUser getById(Long id);
    GetMyUserProfile getMyProfile(Long id);
    GetUser getByUsername(String username);
    GetUser getByEmail(String email);
    GetUser save(SaveUser saveUser);
    GetUser updateById(Long id, SaveUser saveUser);
    GetUser desactivate(Long id);
}
