package com.devops.backend.rewear.dtos.request;

import jakarta.validation.constraints.NotNull;

import java.io.Serializable;

public record SaveExchange(
        @NotNull(message = "debe solicitar una prenda en un intercambio")
        Long requestedWearId,
        @NotNull(message = "debe ofrecer una prenda en un intercambio")
        Long offeredWearId
) implements Serializable {
}
