package com.devops.backend.rewear.dtos.request;

import com.devops.backend.rewear.entities.enums.Genre;
import com.devops.backend.rewear.entities.enums.WearCategory;
import com.devops.backend.rewear.entities.enums.WearCondition;
import com.devops.backend.rewear.entities.enums.WearSize;

import java.io.Serializable;

public record WearFilter(
        String name,
        WearCategory category, //SHIRT, PANTS, SHOES, ACCESSORY
        Genre genre, //MALE, FEMALE, OTHER
        String color,
        WearSize size, //XS, S, M, L, XL, XXL, XXXL, XXXXL, U
        WearCondition condition, //NEW, LIKE_NEW, GOOD, FAIR
        String brand,
        String material
) implements Serializable {
}
