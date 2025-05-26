package com.niyiment.urlshortener.service;

import com.niyiment.urlshortener.dto.UrlCreationRequest;
import com.niyiment.urlshortener.dto.UrlResponse;

import java.util.List;

public interface UrlService {
    UrlResponse createShortUrl(UrlCreationRequest request);

    String getOriginalUrl(String shortUrl);

    void deleteUrl(String shortUrl);

    List<UrlResponse> getAllUrls();

    void cleanupExpiredUrls();
}
