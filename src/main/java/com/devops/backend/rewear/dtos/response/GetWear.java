package com.devops.backend.rewear.dtos.response;

import com.devops.backend.rewear.entities.User;
import com.devops.backend.rewear.entities.enums.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.time.LocalDateTime;

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
        Long ownerId
) implements Serializable {
}
