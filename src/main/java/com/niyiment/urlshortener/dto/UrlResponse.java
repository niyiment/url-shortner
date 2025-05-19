package com.niyiment.urlshortener.dto;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record UrlResponse(
    String shortUrl,
    String originalUrl,
    LocalDateTime expiresAt,
    LocalDateTime createdAt,
    long accessCount
) {
}
