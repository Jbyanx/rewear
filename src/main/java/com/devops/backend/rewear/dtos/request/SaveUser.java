package com.devops.backend.rewear.dtos.request;

import com.devops.backend.rewear.entities.enums.DocumentType;
import com.devops.backend.rewear.entities.enums.Genre;

import java.io.Serializable;
import java.time.LocalDate;

public record SaveUser(
        String firstName,
        String lastName,
        String phoneNumber,
        String email,
        String username,
        String password,
        String address,
        String city,
        String country,
        LocalDate birthDate,
        Genre genre,
        DocumentType documentType,
        String documentNumber,
        String profileImageUrl
) implements Serializable {
}
