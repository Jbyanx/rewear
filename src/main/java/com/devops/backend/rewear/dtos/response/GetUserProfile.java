package com.devops.backend.rewear.dtos.response;

import com.devops.backend.rewear.entities.enums.DocumentType;
import com.devops.backend.rewear.entities.enums.Genre;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record GetUserProfile(
        Long id,
        String firstName,
        String lastName,
        String phoneNumber,
        String email,
        String username,
        String address,
        String city,
        String country,
        LocalDate birthDate,
        Genre genre,
        DocumentType documentType,
        String documentNumber,
        String profileImageUrl,
        Double rating,
        Integer totalRatings,
        LocalDateTime createdAt,
        String role
) implements Serializable {
}
