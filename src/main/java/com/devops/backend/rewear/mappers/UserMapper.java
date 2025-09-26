package com.devops.backend.rewear.mappers;

import com.devops.backend.rewear.dtos.request.SaveUser;
import com.devops.backend.rewear.dtos.response.GetUserProfile;
import com.devops.backend.rewear.dtos.response.GetUser;
import com.devops.backend.rewear.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    // DTO de creación → Entidad
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "rating", ignore = true)
    @Mapping(target = "totalRatings", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "isActive", ignore = true) // se inicializa con true en la entidad
    @Mapping(target = "role", ignore = true)   // lo asignamos en el servicio
    User toEntity(SaveUser dto);

    // Entidad → Perfil completo (para el dueño de la cuenta)
    @Mapping(target = "role", source = "role") // string
    GetUserProfile toGetMyUserProfile(User entity);

    // Entidad → Perfil reducido (para mostrar a otros)
    GetUser toGetUser(User entity);
}
