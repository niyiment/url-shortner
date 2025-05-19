package com.niyiment.urlshortener.service.impl;

import com.niyiment.urlshortener.dto.UrlCreationRequest;
import com.niyiment.urlshortener.dto.UrlResponse;
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
        String shortUrl;

        if (request.customAlias() != null && !request.customAlias().isEmpty()) {
            Optional<Url> existingUrl = urlRepository.findByShortUrl(request.customAlias());
            if (existingUrl.isPresent()) {
                throw new IllegalArgumentException("Custom alias already exists");
            }
            shortUrl = request.customAlias();
        } else {
            Url url = new Url(null, request.originUrl(),request.expiresAt());
            url = urlRepository.save(url);

            shortUrl = base62Converter.encode(url.getId());
            url.setShortUrl(shortUrl);
            url = urlRepository.save(url);
        }

        Url savedUrl = urlRepository.findByShortUrl(shortUrl)
                .orElseThrow(() -> new RuntimeException("Error creating short URL"));

        return convertToUrlResponse(savedUrl);
    }

    @Override
    @Cacheable(value = "urls", key = "#shortUrl")
    @Transactional
    public String getOriginalUrl(String shortUrl) {
        Url url = urlRepository.findByShortUrl(shortUrl)
                .orElseThrow(() -> new RuntimeException("URL not found"));

        if (url.getExpirationAt().isAfter(LocalDateTime.now())) {
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
        Url url = urlRepository.findByShortUrl(shortUrl)
                .orElseThrow(() -> new RuntimeException("URL not found"));

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
                .shortUrl(BASE_URL + url.getShortUrl())
                .originalUrl(url.getOriginalUrl())
                .expiresAt(url.getExpirationAt())
                .createdAt(url.getCreatedAt())
                .accessCount(url.getAccessCount())
                .build();
    }
}
