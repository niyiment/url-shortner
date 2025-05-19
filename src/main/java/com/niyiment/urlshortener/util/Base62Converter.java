package com.niyiment.urlshortener.util;


import org.springframework.stereotype.Component;

@Component
public class Base62Converter {
    private static final String CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int BASE = CHARACTERS.length();

    public String encode(Long value) {
        if (value == null || value < 0) {
            throw new IllegalArgumentException("Number must be non-negative");
        }

        if (value == 0){
            return String.valueOf(CHARACTERS.charAt(0));
        }

        StringBuilder sb = new StringBuilder();
        while (value > 0) {
            sb.append(CHARACTERS.charAt((int) (value % BASE)));
            value /= BASE;
        }
        return sb.reverse().toString();
    }

    public Long decode(String value) {
        if (value == null || value.isEmpty()) {
            throw new IllegalArgumentException("Value must not be empty");
        }

        long result = 0;
        for (int i=0; i < value.length(); i++) {
            result = result * BASE + CHARACTERS.indexOf(value.charAt(i));
        }

        return result;
    }
}
