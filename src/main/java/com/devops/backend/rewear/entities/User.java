package com.devops.backend.rewear.entities;

import com.devops.backend.rewear.entities.enums.DocumentType;
import com.devops.backend.rewear.entities.enums.Genre;
import com.devops.backend.rewear.entities.enums.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

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
    @NotBlank(message = "El nombre no puede estar vacío")
    @Size(min = 3, max = 50, message = "El nombre debe tener entre {min} y {max} caracteres")
    private String firstName;
    @NotBlank(message = "El apellido no puede estar vacío")
    @Size(min = 3, max = 50, message = "El apellido debe tener entre {min} y {max} caracteres")
    private String lastName;
    @NotBlank(message = "El numero de telefono no puede estar vacío")
    @Size(min = 6, max = 20, message = "El numero de telefono debe tener entre {min} y {max} caracteres")
    private String phoneNumber;
    @NotBlank(message = "El correo electronico es obligatorio")
    @Email(message = "Formato de correo inválido")
    @Column(unique = true, nullable = false)
    private String email;
    @NotBlank(message = "El username no debe estar vacío")
    @Column(unique = true, nullable = false)
    private String username;
    @NotBlank(message = "La contraseña no debe estar vacía")
    @Size(min = 8, max = 72, message = "La contraseña debe tener entre {min} y {max} caracteres")
    private String password;
    @NotBlank(message = "La direccion no debe estar vacía")
    @Size(min = 6, max = 150, message = "La direccion debe tener entre {min} y {max} caracteres")
    private String address;
    @NotBlank(message = "La ciudad no debe estar vacía")
    @Size(min = 3, max = 50, message = "La ciudad debe tener entre {min} y {max} caracteres")
    private String city;
    @NotBlank(message = "El pais no debe estar vacía")
    @Size(min = 3, max = 50, message = "El pais debe tener entre {min} y {max} caracteres")
    private String country;
    @Past
    @NotNull
    private LocalDate birthDate;
    @Enumerated(EnumType.STRING)
    @NotNull
    private Genre genre;
    @Enumerated(EnumType.STRING)
    @NotNull
    private DocumentType documentType;
    @NotBlank(message = "El numero de documento no debe estar vacio")
    @Size(min = 5, max = 25, message = "El numero del documento debe tener entre {min} y {max} caracteres")
    private String documentNumber;
    //@NotBlank(message = "La imagen del perfil no debe estar vacía")
    private String profileImageUrl;
    //aca esta la informacion de cada uno de los ratings con su fecha y autor
    //es el porcentaje de rating total calculado
    private Double rating = 0.0;
    //numero total de ratings
    private Integer totalRatings = 0;
    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;
    @Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT TRUE")
    private boolean isActive = true;
    @NotNull
    @Enumerated(EnumType.STRING)
    private Role role;
}
