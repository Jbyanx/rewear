package com.devops.backend.rewear.mappers;

import com.devops.backend.rewear.dtos.request.SaveExchange;
import com.devops.backend.rewear.dtos.response.GetExchange;
import com.devops.backend.rewear.entities.Exchange;
import com.devops.backend.rewear.entities.User;
import com.devops.backend.rewear.entities.Wear;
import org.mapstruct.*;
import java.util.List;

@Mapper(componentModel = "spring")
public interface ExchangeMapper {

    // ======SaveExchange → Exchange ======
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", expression = "java(com.devops.backend.rewear.entities.enums.ExchangeStatus.PENDING)")
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
    @Mapping(target = "requesterId", source = "requester.id")
    @Mapping(target = "ownerId", source = "owner.id")
    @Mapping(target = "offeredWearId", source = "offeredWear.id")
    @Mapping(target = "requestedWearId", source = "requestedWear.id")
    @Mapping(target = "reviews", ignore = true) // se puede mapear luego
    GetExchange toGetExchange(Exchange entity);

    List<GetExchange> toGetDtoList(List<Exchange> exchanges);

    // ====== 3️⃣ Helpers ======
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

