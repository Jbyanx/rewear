package com.devops.backend.rewear.entities;

import com.devops.backend.rewear.entities.enums.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "wears")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Wear {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre no puede estar vacío")
    @Size(min = 5, max = 150, message = "El nombre debe tener entre {min} y {max} caracteres")
    @Column(nullable = false, length = 150)
    private String name;

    @NotBlank(message = "La descripcion no puede estar vacía")
    @Size(min = 5, max = 500, message = "El nombre debe tener entre {min} y {max} caracteres")
    @Column(nullable = false, length = 500)
    private String description;

    @NotNull(message = "La talla no debe estar vacía")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private WearSize size;

    @NotNull(message = "La condicion de la prenda no debe estar vacía")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private WearCondition condition; // NEW, LIKE_NEW, GOOD, FAIR.

    @NotNull(message = "El estado de la prenda no debe estar vacío")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private WearStatus status; // AVAILABLE, RESERVED, EXCHANGED, DELETED

    @NotNull(message = "La categoria de la prenda no debe estar vacía")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private WearCategory category; //SHIRT, PANTS, SHOES, ACCESSORY

    @NotBlank
    @Size(min = 2, max = 100, message = "La marca de la prenda debe tener entre {min} y {max} caracteres")
    @Column(nullable = false, length = 100)
    private String brand;

    @NotBlank
    @Size(min = 4, max = 50, message = "El color de la prenda debe tener entre {min} y {max} caracteres")
    @Column(nullable = false, length = 50)
    private String color;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private Genre genre;

    @NotBlank
    @Size(min = 4, max = 50, message = "El material de la prenda debe tener entre {min} y {max} caracteres")
    private String material;

    private Boolean active = true;

    private LocalDateTime deletedAt;

    @NotNull
    @Column(name = "image_url", length = 255)
    private String imageUrl;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    private User owner;
}
