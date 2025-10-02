package com.devops.backend.rewear.dtos.request;

import com.devops.backend.rewear.entities.enums.DocumentType;
import com.devops.backend.rewear.entities.enums.Genre;
import jakarta.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;

public record UpdateUser(
        @Size(min = 3, max = 50, message = "El nombre debe tener entre {min} y {max} caracteres")
        String firstName,

        @Size(min = 3, max = 50, message = "El apellido debe tener entre {min} y {max} caracteres")
        String lastName,

        @Size(min = 6, max = 20, message = "El número de teléfono debe tener entre {min} y {max} caracteres")
        String phoneNumber,

        @Email(message = "Formato de correo inválido")
        String email,

        String username,

        @Size(min = 6, max = 150, message = "La dirección debe tener entre {min} y {max} caracteres")
        String address,

        @Size(min = 3, max = 50, message = "La ciudad debe tener entre {min} y {max} caracteres")
        String city,

        @Size(min = 3, max = 50, message = "El país debe tener entre {min} y {max} caracteres")
        String country,

        @Past(message = "La fecha de nacimiento debe ser en el pasado")
        LocalDate birthDate,

        Genre genre,

        DocumentType documentType,

        @Size(min = 5, max = 25, message = "El número de documento debe tener entre {min} y {max} caracteres")
        String documentNumber,

        @Size(max = 255, message = "La URL de la imagen de perfil no puede superar los {max} caracteres")
        String profileImageUrl
) implements Serializable {
}
