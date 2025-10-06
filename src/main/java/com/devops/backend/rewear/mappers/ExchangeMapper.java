package com.devops.backend.rewear.mappers;

import com.devops.backend.rewear.dtos.request.SaveExchange;
import com.devops.backend.rewear.dtos.response.GetExchange;
import com.devops.backend.rewear.entities.Exchange;
import com.devops.backend.rewear.entities.User;
import com.devops.backend.rewear.entities.Wear;
import com.devops.backend.rewear.entities.enums.ExchangeStatus;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring", uses = {UserMapper.class, WearMapper.class})
public interface ExchangeMapper {

    // ====== SaveExchange → Exchange ======
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", expression = "java(ExchangeStatus.PENDING)")
    @Mapping(target = "requesterConfirmed", constant = "false")
    @Mapping(target = "ownerConfirmed", constant = "false")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "requester", ignore = true) // se setea en el servicio
    @Mapping(target = "owner", ignore = true)     // se setea en el servicio
    @Mapping(target = "offeredWear", source = "offeredWearId", qualifiedByName = "wearFromId")
    @Mapping(target = "requestedWear", source = "requestedWearId", qualifiedByName = "wearFromId")
    Exchange toExchange(SaveExchange dto);

    // ====== Exchange → GetExchange ======
    @Mapping(target = "requester", source = "requester", qualifiedByName = "toGetSimpleUser")
    @Mapping(target = "owner", source = "owner", qualifiedByName = "toGetSimpleUser")
    @Mapping(target = "offeredWear", source = "offeredWear", qualifiedByName = "toGetWear")
    @Mapping(target = "requestedWear", source = "requestedWear", qualifiedByName = "toGetWear")
    @Mapping(target = "reviews", ignore = true) // se mapeará cuando tengas la entidad Review
    GetExchange toGetExchange(Exchange entity);

    List<GetExchange> toGetExchangeList(List<Exchange> entities);

    // ====== Métodos auxiliares ======
    @Named("wearFromId")
    default Wear wearFromId(Long id) {
        if (id == null) return null;
        Wear wear = new Wear();
        wear.setId(id);
        return wear;
    }

    @Named("userFromId")
    default User userFromId(Long id) {
        if (id == null) return null;
        User user = new User();
        user.setId(id);
        return user;
    }
}
