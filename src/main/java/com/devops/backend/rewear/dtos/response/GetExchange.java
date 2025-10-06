package com.devops.backend.rewear.dtos.response;

import com.devops.backend.rewear.entities.enums.ExchangeStatus;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

public record GetExchange(
    Long id,
    Long requesterId,
    Long ownerId,
    Long offeredWearId,
    Long requestedWearId,
    ExchangeStatus status,
    LocalDateTime createdAt,
    LocalDateTime updatedAt,
    List<GetReview> reviews
) implements Serializable {
}
