package com.niyiment.urlshortener.controller;


import com.niyiment.urlshortener.dto.UrlCreationRequest;
import com.niyiment.urlshortener.dto.UrlResponse;
import com.niyiment.urlshortener.service.UrlService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;


@RestController
@RequestMapping(value = "/api/urls")
@RequiredArgsConstructor
public class UrlController {
    private final UrlService urlService;

    @PostMapping
    public ResponseEntity<UrlResponse> createShortUrl(
            @Valid @RequestBody UrlCreationRequest request) {
        UrlResponse response = urlService.createShortUrl(request);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{shortUrl}")
    public RedirectView redirectToOriginalUrl(@PathVariable("shortUrl") String shortUrl) {
        String originalUrl = urlService.getOriginalUrl(shortUrl);

        return new RedirectView(originalUrl);
    }

    @DeleteMapping("/{shortUrl}")
    public ResponseEntity<Void> deleteUrl(@PathVariable("shortUrl") String shortUrl) {
        urlService.deleteUrl(shortUrl);

        return ResponseEntity.noContent().build();
    }
}
