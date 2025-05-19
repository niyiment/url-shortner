package com.niyiment.urlshortener.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDateTime;

public record UrlCreationRequest(
        @NotBlank(message = "Origin URL is required")
        @Pattern(regexp = "^(https?|ftp)://.*$", message = "Origin URL must be a valid URL")
        String originalUrl,
        LocalDateTime expiresAt,
        String customAlias
) {
}
