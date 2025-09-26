package com.devops.backend.rewear.dtos.request;

import com.devops.backend.rewear.entities.enums.DocumentType;
import com.devops.backend.rewear.entities.enums.Genre;
import jakarta.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;

public record SaveUser(
        @NotBlank(message = "El nombre no puede estar vacío")
        @Size(min = 3, max = 50, message = "El nombre debe tener entre {min} y {max} caracteres")
        String firstName,

        @NotBlank(message = "El apellido no puede estar vacío")
        @Size(min = 3, max = 50, message = "El apellido debe tener entre {min} y {max} caracteres")
        String lastName,

        @NotBlank(message = "El número de teléfono no puede estar vacío")
        @Size(min = 6, max = 20, message = "El número de teléfono debe tener entre {min} y {max} caracteres")
        String phoneNumber,

        @NotBlank(message = "El correo electrónico es obligatorio")
        @Email(message = "Formato de correo inválido")
        String email,

        @NotBlank(message = "El username no debe estar vacío")
        String username,

        @NotBlank(message = "La contraseña no debe estar vacía")
        @Size(min = 8, max = 72, message = "La contraseña debe tener entre {min} y {max} caracteres")
        String password,

        @NotBlank(message = "La dirección no debe estar vacía")
        @Size(min = 6, max = 150, message = "La dirección debe tener entre {min} y {max} caracteres")
        String address,

        @NotBlank(message = "La ciudad no debe estar vacía")
        @Size(min = 3, max = 50, message = "La ciudad debe tener entre {min} y {max} caracteres")
        String city,

        @NotBlank(message = "El país no debe estar vacío")
        @Size(min = 3, max = 50, message = "El país debe tener entre {min} y {max} caracteres")
        String country,

        @Past(message = "La fecha de nacimiento debe ser en el pasado")
        @NotNull(message = "La fecha de nacimiento es obligatoria")
        LocalDate birthDate,

        @NotNull(message = "El género es obligatorio")
        Genre genre,

        @NotNull(message = "El tipo de documento es obligatorio")
        DocumentType documentType,

        @NotBlank(message = "El número de documento no debe estar vacío")
        @Size(min = 5, max = 25, message = "El número de documento debe tener entre {min} y {max} caracteres")
        String documentNumber,

        @Size(max = 255, message = "La URL de la imagen de perfil no puede superar los {max} caracteres")
        String profileImageUrl
) implements Serializable {
}
