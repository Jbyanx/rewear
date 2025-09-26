package com.devops.backend.rewear.dtos.response;

import com.devops.backend.rewear.entities.enums.DocumentType;
import com.devops.backend.rewear.entities.enums.Genre;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record LoginResponse(
        String jwt,
        GetUserProfile userProfile
) implements Serializable {
}
