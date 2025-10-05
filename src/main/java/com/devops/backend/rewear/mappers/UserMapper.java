package com.devops.backend.rewear.mappers;

import com.devops.backend.rewear.dtos.request.SaveUser;
import com.devops.backend.rewear.dtos.response.GetUser;
import com.devops.backend.rewear.dtos.response.GetUserProfile;
import com.devops.backend.rewear.dtos.response.GetWear;
import com.devops.backend.rewear.entities.User;
import com.devops.backend.rewear.entities.enums.WearStatus;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = WearMapper.class)
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "rating", ignore = true)
    @Mapping(target = "totalRatings", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "isActive", ignore = true)
    @Mapping(target = "role", ignore = true)
    User toEntity(SaveUser dto);

    // Entidad → Perfil completo (para el dueño de la cuenta)
    @Mapping(target = "role", source = "role")
    @Mapping(target = "wears", source = "wears") // <--- mapea automáticamente con WearMapper
    GetUserProfile toGetUserProfile(User entity);


    // Ahora sí mapeará los wears disponibles automáticamente
    @Mapping(target = "availableWears", expression = "java(mapAvailableWears(entity))")
    GetUser toGetUser(User entity);

    @InheritInverseConfiguration
    User toEntity(GetUserProfile getUser);

    @InheritInverseConfiguration
    SaveUser toSaveUser(User user);

    // Método auxiliar para filtrar solo las prendas disponibles
    default List<GetWear> mapAvailableWears(User user) {
        if (user.getWears() == null) return List.of();
        return user.getWears().stream()
                .filter(w -> w.getStatus() == WearStatus.AVAILABLE)
                .map(w -> new GetWear(
                        w.getId(),
                        w.getName(),
                        w.getDescription(),
                        w.getSize(),
                        w.getCondition(),
                        w.getStatus(),
                        w.getCategory(),
                        w.getBrand(),
                        w.getColor(),
                        w.getGenre(),
                        w.getMaterial(),
                        w.getImageUrl(),
                        w.getOwner() != null ? w.getOwner().getId() : null
                ))
                .toList();
    }
}
