package com.devops.backend.rewear.dtos.response;

import com.devops.backend.rewear.entities.enums.Genre;

import java.io.Serializable;
import java.util.List;

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
        Integer totalRatings,
        List<GetExchange> exchanges, //confirmed
        List<GetWear> wears //available
) implements Serializable {
}
