package com.devops.backend.rewear.dtos.request;

import com.devops.backend.rewear.entities.enums.Genre;
import com.devops.backend.rewear.entities.enums.WearCategory;
import com.devops.backend.rewear.entities.enums.WearCondition;
import com.devops.backend.rewear.entities.enums.WearSize;
import jakarta.validation.constraints.Size;

import java.io.Serializable;

public record UpdateWear(
        @Size(min = 5, max = 150, message = "El nombre debe tener entre {min} y {max} caracteres")
        String name,

        @Size(min = 5, max = 500, message = "El nombre debe tener entre {min} y {max} caracteres")
        String description,

        WearSize size,

        WearCondition condition, // NEW, LIKE_NEW, GOOD, FAIR.

        WearCategory category, //SHIRT, PANTS, SHOES, ACCESSORY

        @Size(min = 2, max = 100, message = "La marca de la prenda debe tener entre {min} y {max} caracteres")
        String brand,

        @Size(min = 4, max = 50, message = "El color de la prenda debe tener entre {min} y {max} caracteres")
        String color,

        Genre genre,

        @Size(min = 4, max = 50, message = "El material de la prenda debe tener entre {min} y {max} caracteres")
        String material,

        @Size(max = 255)
        String imageUrl
) implements Serializable {
}
