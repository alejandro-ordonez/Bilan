package org.bilan.co.config;

import org.bilan.co.utils.Constants;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
@EnableCaching
public class CachingConfig {

    @Bean
    public CacheManager cacheManager() {
        SimpleCacheManager cacheManager = new SimpleCacheManager();
        cacheManager.setCaches(Arrays.asList(
                new ConcurrentMapCache("colleges"),
                new ConcurrentMapCache(Constants.CACHE_COLLEGES_BY_MUN),
                new ConcurrentMapCache(Constants.STATES)));
        return cacheManager;
    }
}
