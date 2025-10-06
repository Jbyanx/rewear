package com.devops.backend.rewear.mappers;

import com.devops.backend.rewear.dtos.request.SaveUser;
import com.devops.backend.rewear.dtos.response.*;
import com.devops.backend.rewear.entities.User;
import com.devops.backend.rewear.entities.enums.WearStatus;
import com.devops.backend.rewear.entities.enums.ExchangeStatus;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Stream;

@Mapper(componentModel = "spring", uses = {WearMapper.class})
public abstract class UserMapper {

    @Autowired
    protected WearMapper wearMapper;

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "rating", ignore = true)
    @Mapping(target = "totalRatings", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "isActive", ignore = true)
    @Mapping(target = "role", ignore = true)
    public abstract User toUser(SaveUser dto);

    @Mapping(target = "wears", source = "wears")
    @Mapping(target = "exchanges", expression = "java(mapAllExchanges(entity))")
    public abstract GetUserProfile toGetUserProfile(User entity);

    @Mapping(target = "availableWears", expression = "java(mapAvailableWears(entity))")
    @Mapping(target = "confirmedExchanges", expression = "java(mapConfirmedExchanges(entity))")
    public abstract GetUser toGetUser(User entity);

    @Named("toGetSimpleUser")
    public abstract GetSimpleUser toGetSimpleUser(User entity);

    // ===== Métodos auxiliares =====
    protected List<GetWear> mapAvailableWears(User user) {
        if (user.getWears() == null) return List.of();
        return user.getWears().stream()
                .filter(w -> w.getStatus() == WearStatus.AVAILABLE)
                .map(w -> wearMapper.toGetWear(w)) // ✔ usamos wearMapper inyectado
                .toList();
    }

    protected List<GetExchange> mapAllExchanges(User user) {
        if (user.getOwnedExchanges() == null && user.getRequestedExchanges() == null) return List.of();
        return Stream.concat(
                        user.getOwnedExchanges() != null ? user.getOwnedExchanges().stream() : Stream.empty(),
                        user.getRequestedExchanges() != null ? user.getRequestedExchanges().stream() : Stream.empty()
                )
                .map(exchange -> new GetExchange(
                        exchange.getId(),
                        toGetSimpleUser(exchange.getRequester()),
                        toGetSimpleUser(exchange.getOwner()),
                        wearMapper.toGetWear(exchange.getOfferedWear()),
                        wearMapper.toGetWear(exchange.getRequestedWear()),
                        exchange.isRequesterConfirmed(),
                        exchange.isOwnerConfirmed(),
                        exchange.getStatus(),
                        exchange.getCreatedAt(),
                        exchange.getUpdatedAt(),
                        List.of()
                ))
                .toList();
    }

    protected List<GetExchange> mapConfirmedExchanges(User user) {
        if (user.getOwnedExchanges() == null && user.getRequestedExchanges() == null) return List.of();
        return Stream.concat(
                        user.getOwnedExchanges() != null ? user.getOwnedExchanges().stream() : Stream.empty(),
                        user.getRequestedExchanges() != null ? user.getRequestedExchanges().stream() : Stream.empty()
                )
                .filter(e -> e.getStatus() == ExchangeStatus.COMPLETED)
                .map(exchange -> new GetExchange(
                        exchange.getId(),
                        toGetSimpleUser(exchange.getRequester()),
                        toGetSimpleUser(exchange.getOwner()),
                        wearMapper.toGetWear(exchange.getOfferedWear()),
                        wearMapper.toGetWear(exchange.getRequestedWear()),
                        exchange.isRequesterConfirmed(),
                        exchange.isOwnerConfirmed(),
                        exchange.getStatus(),
                        exchange.getCreatedAt(),
                        exchange.getUpdatedAt(),
                        List.of()
                ))
                .toList();
    }
}
