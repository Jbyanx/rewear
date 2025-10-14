package com.devops.backend.rewear.dtos.response;

import com.devops.backend.rewear.entities.enums.*;

import java.io.Serializable;

public record GetWear(
        Long id,
        String name,
        String description,
        WearSize size,
        WearCondition condition, // NEW, LIKE_NEW, GOOD, FAIR.
        WearStatus status, // AVAILABLE, RESERVED, EXCHANGED, DELETED
        WearCategory category, //SHIRT, PANTS, SHOES, ACCESSORY
        String brand,
        String color,
        Genre genre,
        String material,
        String imageUrl,
        GetSimpleUser owner
) implements Serializable {
}
