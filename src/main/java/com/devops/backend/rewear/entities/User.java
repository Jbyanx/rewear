package com.devops.backend.rewear.entities;

import com.devops.backend.rewear.entities.enums.DocumentType;
import com.devops.backend.rewear.entities.enums.Genre;
import com.devops.backend.rewear.entities.enums.Role;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private String username;
    private String password;
    private String address;
    private String city;
    private String country;
    private LocalDate birthDate;
    private Genre genre;
    private DocumentType documentType;
    private String documentNumber;
    private String profileImageUrl;
    //aca esta la informacion de cada uno de los ratings con su fecha y autor
    //TODO ojo, no se debe permitir a un usuario hacer dos veces un rating a un intercambio
    private List<Rating> myRatings;
    //es el porcentaje de rating total calculado
    private Double rating;
    //numero total de ratings
    private Integer totalRatings;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Boolean isActive;
    private Role role;
}
