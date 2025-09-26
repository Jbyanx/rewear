package com.devops.backend.rewear.entities;

import com.devops.backend.rewear.entities.enums.DocumentType;
import com.devops.backend.rewear.entities.enums.Genre;
import com.devops.backend.rewear.entities.enums.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

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

    @NotBlank(message = "El nombre no puede estar vacío")
    @Size(min = 3, max = 50, message = "El nombre debe tener entre {min} y {max} caracteres")
    @Column(name = "first_name", nullable = false, length = 50)
    private String firstName;

    @NotBlank(message = "El apellido no puede estar vacío")
    @Size(min = 3, max = 50, message = "El apellido debe tener entre {min} y {max} caracteres")
    @Column(name = "last_name", nullable = false, length = 50)
    private String lastName;

    @NotBlank(message = "El numero de telefono no puede estar vacío")
    @Size(min = 6, max = 20, message = "El numero de telefono debe tener entre {min} y {max} caracteres")
    @Column(name = "phone_number", nullable = false, length = 20)
    private String phoneNumber;

    @NotBlank(message = "El correo electronico es obligatorio")
    @Email(message = "Formato de correo inválido")
    @Column(name = "email", unique = true, nullable = false, length = 150)
    private String email;

    @NotBlank(message = "El username no debe estar vacío")
    @Column(name = "username", unique = true, nullable = false, length = 100)
    private String username;

    @NotBlank(message = "La contraseña no debe estar vacía")
    @Size(min = 8, max = 72, message = "La contraseña debe tener entre {min} y {max} caracteres")
    @Column(name = "password", nullable = false, length = 72)
    private String password;

    @NotBlank(message = "La direccion no debe estar vacía")
    @Size(min = 6, max = 150, message = "La direccion debe tener entre {min} y {max} caracteres")
    @Column(name = "address", nullable = false, length = 150)
    private String address;

    @NotBlank(message = "La ciudad no debe estar vacía")
    @Size(min = 3, max = 50, message = "La ciudad debe tener entre {min} y {max} caracteres")
    @Column(name = "city", nullable = false, length = 50)
    private String city;

    @NotBlank(message = "El pais no debe estar vacía")
    @Size(min = 3, max = 50, message = "El pais debe tener entre {min} y {max} caracteres")
    @Column(name = "country", nullable = false, length = 50)
    private String country;

    @Past
    @NotNull
    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;

    @Enumerated(EnumType.STRING)
    @NotNull
    @Column(name = "genre", nullable = false, length = 20)
    private Genre genre;

    @Enumerated(EnumType.STRING)
    @NotNull
    @Column(name = "document_type", nullable = false, length = 20)
    private DocumentType documentType;

    @NotBlank(message = "El numero de documento no debe estar vacio")
    @Size(min = 5, max = 25, message = "El numero del documento debe tener entre {min} y {max} caracteres")
    @Column(name = "document_number", nullable = false, length = 25)
    private String documentNumber;

    @Column(name = "profile_image_url", length = 255)
    private String profileImageUrl;

    // Cambiado a BigDecimal para coincidir con DECIMAL(5,2) del SQL
    @Column(name = "rating", precision = 5, scale = 2)
    private BigDecimal rating;

    @Column(name = "total_ratings")
    private Integer totalRatings;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false, columnDefinition = "DATETIME2 DEFAULT SYSUTCDATETIME()")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", columnDefinition = "DATETIME2 DEFAULT SYSUTCDATETIME()")
    private LocalDateTime updatedAt;

    @Column(name = "is_active", nullable = false, columnDefinition = "BIT DEFAULT 1")
    private boolean isActive = true;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false, length = 30)
    private Role role;
}
