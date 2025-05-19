package com.niyiment.urlshortener.service.impl;

import com.niyiment.urlshortener.dto.UrlCreationRequest;
import com.niyiment.urlshortener.dto.UrlResponse;
import com.niyiment.urlshortener.exception.UrlNotFoundException;
import com.niyiment.urlshortener.model.Url;
import com.niyiment.urlshortener.repository.UrlRepository;
import com.niyiment.urlshortener.service.UrlService;
import com.niyiment.urlshortener.util.Base62Converter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.Cache;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Slf4j
@Service
@RequiredArgsConstructor
public class UrlServiceImpl implements UrlService {
    private final UrlRepository urlRepository;
    private final Base62Converter base62Converter;

    private static final String BASE_URL = "http://short.url/";

    @Override
    @Transactional
    public UrlResponse createShortUrl(UrlCreationRequest request) {
        if (!isValidUrl(request.originalUrl())) {
            throw new IllegalArgumentException("Invalid URL");
        }

        String shortCode = determineShortCode(request);
        LocalDateTime expiresAt = request.expiresAt() != null ?
                request.expiresAt() : LocalDateTime.now().plusDays(7);

        Url url = new Url("", request.originalUrl(),request.expiresAt());
        url.setShortCode(shortCode);
        url.setExpiresAt(expiresAt);
        url.setCreatedAt(LocalDateTime.now());
        urlRepository.save(url);

        Url savedUrl = urlRepository.findByShortCode(shortCode)
                .orElseThrow(() -> new RuntimeException("Error creating short URL"));

        return convertToUrlResponse(savedUrl);
    }

    @Override
    @Cacheable(value = "urls", key = "#shortUrl")
    @Transactional
    public String getOriginalUrl(String shortUrl) {
        Url url = urlRepository.findByShortCode(shortUrl)
                .orElseThrow(() -> new UrlNotFoundException("URL not found"));

        if (url.getExpiresAt().isAfter(LocalDateTime.now())) {
            urlRepository.incrementAccessCount(url.getId());
            return url.getOriginalUrl();
        }

        urlRepository.incrementAccessCount(url.getId());

        return url.getOriginalUrl();
    }

    @Override
    @CacheEvict(value = "urls", key = "#shortUrl")
    @Transactional
    public void deleteUrl(String shortUrl) {
        Url url = urlRepository.findByShortCode(shortUrl)
                .orElseThrow(() -> new UrlNotFoundException("URL not found"));

        urlRepository.delete(url);
    }

    @Override
    @Scheduled(cron = "0 0 0 * * *")
    @CacheEvict(value = "urls", allEntries = true)
    @Transactional
    public void cleanupExpiredUrls() {
        List<Url> expiredUrls = urlRepository.findExpiredUrls(LocalDateTime.now());
        urlRepository.deleteAll(expiredUrls);
    }

    private UrlResponse convertToUrlResponse(Url url) {
        return UrlResponse.builder()
                .shortUrl(BASE_URL + url.getShortCode())
                .originalUrl(url.getOriginalUrl())
                .expiresAt(url.getExpiresAt())
                .createdAt(url.getCreatedAt())
                .accessCount(url.getAccessCount())
                .build();
    }

    private boolean isValidUrl(String originalUrl) {
        try {
            new URL(originalUrl).toURI();

            return true;
        } catch (Exception exception) {
            return false;
        }
    }

    private String determineShortCode(UrlCreationRequest request) {
        if (request.customAlias() != null && !request.customAlias().isEmpty()) {
            if (urlRepository.existsByShortCode(request.customAlias())) {
                throw new IllegalArgumentException("Custom alias already exists");
            }
            return request.customAlias();
        }

        String shortCode;
        do {
            shortCode = base62Converter.encode(System.currentTimeMillis() % 1000000);
        } while (urlRepository.existsByShortCode(shortCode));
        return shortCode;
    }
}
