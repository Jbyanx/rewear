package com.devops.backend.rewear.dtos.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;

public record SaveReview(
        @NotNull
        @Min(1)
        @Max(5)
        int rating,
        @NotNull
        String comment
) implements Serializable {
}
