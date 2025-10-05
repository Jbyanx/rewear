package com.devops.backend.rewear.dtos.request;

import com.devops.backend.rewear.entities.enums.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serializable;

public record SaveWear(
        @NotBlank(message = "El nombre no puede estar vacío")
        @Size(min = 5, max = 150, message = "El nombre debe tener entre {min} y {max} caracteres")
        String name,

        @NotBlank(message = "La descripcion no puede estar vacía")
        @Size(min = 5, max = 500, message = "El nombre debe tener entre {min} y {max} caracteres")
        String description,

        @NotNull(message = "La talla no debe estar vacía")
        WearSize size,

        @NotNull(message = "La condicion de la prenda no debe estar vacía")
        WearCondition condition, // NEW, LIKE_NEW, GOOD, FAIR.

        @NotNull(message = "La categoria de la prenda no debe estar vacía")
        WearCategory category, //SHIRT, PANTS, SHOES, ACCESSORY

        @NotBlank
        @Size(min = 4, max = 100, message = "La marca de la prenda debe tener entre {min} y {max} caracteres")
        String brand,

        @NotBlank
        @Size(min = 4, max = 50, message = "El color de la prenda debe tener entre {min} y {max} caracteres")
        String color,

        @NotNull(message = "El genero no debe estár vacio")
        Genre genre,

        @NotBlank
        @Size(min = 4, max = 50, message = "El material de la prenda debe tener entre {min} y {max} caracteres")
        String material,

        @NotNull(message = "La imagen de la prenda no puede estar vacia")
        @Size(max = 255)
        String imageUrl
) implements Serializable {
}
