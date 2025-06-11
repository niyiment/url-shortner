package com.niyiment.urlshortener.dto;

import java.time.LocalDateTime;

public record ErrorResponse (
        int value,
        String message,
        LocalDateTime timestamp
){
}
