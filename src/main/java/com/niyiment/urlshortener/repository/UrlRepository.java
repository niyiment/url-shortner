package com.niyiment.urlshortener.repository;

import com.niyiment.urlshortener.model.Url;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Repository
public interface UrlRepository extends JpaRepository<Url, Long> {
    Optional<Url> findByShortUrl(String shortUrl);

    @Modifying
    @Transactional
    @Query("UPDATE Url u SET u.accessCount = u.accessCount + 1 WHERE u.id = :id")
    void incrementAccessCount(@Param("id") Long id);

    List<Url> findExpiredUrls(LocalDateTime now);
}
