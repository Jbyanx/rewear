package com.devops.backend.rewear.services.impl;

import com.devops.backend.rewear.dtos.request.SaveWear;
import com.devops.backend.rewear.dtos.request.UpdateWear;
import com.devops.backend.rewear.dtos.request.WearFilter;
import com.devops.backend.rewear.dtos.response.GetWear;
import com.devops.backend.rewear.entities.User;
import com.devops.backend.rewear.entities.Wear;
import com.devops.backend.rewear.entities.enums.Role;
import com.devops.backend.rewear.entities.enums.WearStatus;
import com.devops.backend.rewear.exceptions.PermissionDeniedException;
import com.devops.backend.rewear.exceptions.WearNotFoundException;
import com.devops.backend.rewear.mappers.WearMapper;
import com.devops.backend.rewear.repositories.WearRepository;
import com.devops.backend.rewear.services.WearService;
import com.devops.backend.rewear.specification.WearSpecification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class WearServiceImpl implements WearService {
    private final WearRepository wearRepository;
    private final CurrentUserService currentUserService;
    private final WearMapper wearMapper;

    public WearServiceImpl(WearRepository wearRepository, CurrentUserService currentUserService, WearMapper wearMapper) {
        this.wearRepository = wearRepository;
        this.currentUserService = currentUserService;
        this.wearMapper = wearMapper;
    }

    @Override
    public GetWear createWear(SaveWear saveWear) {
        User principal = currentUserService.getAuthenticatedUser();

        Wear wear = Wear.builder()
                .name(saveWear.name())
                .description(saveWear.description())
                .size(saveWear.size())
                .condition(saveWear.condition())
                .status(WearStatus.AVAILABLE)
                .category(saveWear.category())
                .brand(saveWear.brand())
                .color(saveWear.color())
                .genre(saveWear.genre())
                .material(saveWear.material())
                .imageUrl(saveWear.imageUrl())
                .owner(principal)
                .active(true)
                .build();
        return wearMapper.toGetWear(wearRepository.save(wear));
    }

    @Override
    public List<GetWear> getAvailableWears(WearFilter filter) {
        return wearRepository.findAll(WearSpecification.byFilter(filter))
                .stream()
                .map(wearMapper::toGetWear)
                .collect(Collectors.toList());
    }

    @Override
    public GetWear updateStatus(Long id, boolean active) {
        User principal = currentUserService.getAuthenticatedUser();
        Wear wear = wearRepository.findById(id)
                .orElseThrow(() -> new WearNotFoundException(" Error al actualizar prenda id: "+ id));

        if(!(wear.getOwner().getId().equals(principal.getId())) || principal.getRole().equals(Role.ADMIN)){
            throw new PermissionDeniedException("vaya, no tienes permisos para actualizar este recurso");
        }
        wear.setActive(active);
        return wearMapper.toGetWear(wearRepository.save(wear));
    }

    @Override
    public GetWear updateWear(Long id, UpdateWear updateWear) {
        User user = currentUserService.getAuthenticatedUser();
        Wear wear = wearRepository.findById(id)
                .orElseThrow(() -> new WearNotFoundException(" Error al actualizar prenda id: "+ id+", no existe en BDD"));

        if(!(user.getRole().equals(Role.ADMIN) || user.getId().equals(wear.getOwner().getId()))) {
            throw new PermissionDeniedException("vaya, no tienes permisos para actualizar este recurso");
        }

        if (!updateWear.name().isEmpty()) wear.setName(updateWear.name());
        if (!updateWear.description().isEmpty()) wear.setDescription(updateWear.description());
        if (updateWear.size() != null) wear.setSize(updateWear.size());
        if(updateWear.condition() != null) wear.setCondition(updateWear.condition());
        if(updateWear.category() != null) wear.setCategory(updateWear.category());
        if (!updateWear.brand().isEmpty()) wear.setBrand(updateWear.brand());
        if (!updateWear.color().isEmpty()) wear.setColor(updateWear.color());
        if(updateWear.genre() != null) wear.setGenre(updateWear.genre());
        if (!updateWear.material().isEmpty()) wear.setMaterial(updateWear.material());
        if (!updateWear.imageUrl().isEmpty()) wear.setImageUrl(updateWear.imageUrl());

        return wearMapper.toGetWear(wearRepository.save(wear));
    }
}
