package com.devops.backend.rewear.mappers;

import com.devops.backend.rewear.dtos.request.SaveWear;
import com.devops.backend.rewear.dtos.response.GetWear;
import com.devops.backend.rewear.entities.User;
import com.devops.backend.rewear.entities.Wear;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {
       UserMapper.class
})
public interface WearMapper {

    /**
     * Convierte un DTO SaveWear en una entidad Wear.
     * Se ignoran campos automáticos como id, createdAt, updatedAt y deletedAt.
     * Se configura el status por defecto como AVAILABLE.
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "owner", ignore = true)
    Wear toWear(SaveWear saveWear);

    /**
     * Convierte una entidad Wear a su DTO GetWear.
     * Solo se expone el id del owner, no todo el objeto User.
     */
    @Mapping(target = "owner", source = "owner")
    @Named("toGetWear")
    GetWear toGetWear(Wear entity);


}