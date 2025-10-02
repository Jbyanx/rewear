package com.devops.backend.rewear.dtos.response;

import java.io.Serializable;

public record LoginResponse(
        String accessToken,
        String refreshToken,
        GetUserProfile userProfile
) implements Serializable {
}