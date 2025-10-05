package com.devops.backend.rewear.dtos.response;

import java.time.Instant;

public record ApiErrorResponse(
        String error,
        String message,
        int status,
        Instant timestamp,
        String path
) {
    public static ApiErrorResponse of(String error, String message, int status, String path) {
        return new ApiErrorResponse(error, message, status, Instant.now(), path);
    }
}
