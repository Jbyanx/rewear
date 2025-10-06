package com.devops.backend.rewear.dtos.response;

import com.devops.backend.rewear.entities.enums.Genre;

import java.io.Serializable;

public record GetSimpleUser(
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
