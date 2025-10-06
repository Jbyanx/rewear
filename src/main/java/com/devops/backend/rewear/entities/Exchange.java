package com.devops.backend.rewear.entities;

import com.devops.backend.rewear.entities.enums.ExchangeStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "exchanges")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Exchange {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Usuario que solicita el intercambio
    @NotNull(message = "El solicitante no puede ser nulo")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "requester_id", nullable = false)
    private User requester;

    // Usuario dueño de la prenda solicitada
    @NotNull(message = "El dueño no puede ser nulo")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;

    // Prenda ofrecida por el solicitante
    @NotNull(message = "Debe ofrecer una prenda para el intercambio")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "offered_wear_id", nullable = false)
    private Wear offeredWear;

    // Prenda que el solicitante desea obtener
    @NotNull(message = "Debe seleccionar una prenda solicitada")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "requested_wear_id", nullable = false)
    private Wear requestedWear;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ExchangeStatus status = ExchangeStatus.PENDING;

    @Column(nullable = false)
    private boolean requesterConfirmed = false;

    @Column(nullable = false)
    private boolean ownerConfirmed = false;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

}
