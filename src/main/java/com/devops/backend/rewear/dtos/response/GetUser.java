package com.devops.backend.rewear.dtos.response;

import com.devops.backend.rewear.entities.enums.DocumentType;
import com.devops.backend.rewear.entities.enums.Genre;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record GetUser(
        Long id,
        String firstName,
        String lastName,
        String username,
        String city,
        String country,
        Genre genre,
        String profileImageUrl,
        Double rating,
        Integer totalRatings
) implements Serializable {
}
