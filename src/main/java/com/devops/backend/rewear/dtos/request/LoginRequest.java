package com.devops.backend.rewear.dtos.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;

public record LoginRequest(
        @NotNull
        String username,
        @NotNull
        String password
) implements Serializable {
}
