package com.devops.backend.rewear.dtos.request;

import java.io.Serializable;

public record RefreshToken(
        String refreshToken
) implements Serializable {
}
