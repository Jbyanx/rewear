package com.devops.backend.rewear.dtos.response;

import java.io.Serializable;

public record LoginResponse(
        String jwt,
        Long idUser
) implements Serializable {
}
