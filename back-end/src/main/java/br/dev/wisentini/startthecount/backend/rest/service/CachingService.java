package br.dev.wisentini.startthecount.backend.rest.service;

import lombok.RequiredArgsConstructor;

import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CachingService {

    private final CacheManager cacheManager;

    public void evictAllCaches() {
        this.cacheManager
            .getCacheNames()
            .forEach(cacheName -> Objects.requireNonNull(this.cacheManager.getCache(cacheName)).clear());
    }
}
