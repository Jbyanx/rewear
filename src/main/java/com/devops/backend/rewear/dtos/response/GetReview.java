package com.devops.backend.rewear.dtos.response;

import java.io.Serializable;
import java.time.LocalDateTime;

public record GetReview(
        Long id,
        GetSimpleUser reviewer,
        GetSimpleUser reviewed,
        int rating,
        String comment,
        LocalDateTime createdAt
) implements Serializable {
}