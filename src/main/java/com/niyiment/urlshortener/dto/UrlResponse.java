package com.niyiment.urlshortener.dto;

import java.time.LocalDateTime;

public record UrlResponse(
    String shortUrl,
    String originalUrl,
    LocalDateTime expiresAt,
    LocalDateTime createdAt,
    long accessCount
) {
}
