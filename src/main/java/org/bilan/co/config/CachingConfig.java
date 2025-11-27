package org.bilan.co.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.bilan.co.utils.Constants;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

@Configuration
@EnableCaching
public class CachingConfig {

    @Bean
    public CacheManager cacheManager() {
        SimpleCacheManager cacheManager = new SimpleCacheManager();
        cacheManager.setCaches(Arrays.asList(
                // Reference data caches - expire after 24 hours (rarely change)
                buildCache("colleges", 24, TimeUnit.HOURS),
                buildCache(Constants.CACHE_COLLEGES_BY_MUN, 6, TimeUnit.HOURS),
                buildCache(Constants.COLLEGES_BY_STATE, 6, TimeUnit.HOURS),
                buildCache(Constants.GRADES_COURSES, 6, TimeUnit.HOURS),
                buildCache(Constants.GAME_INFO, 24, TimeUnit.HOURS),
                buildCache(Constants.GAME_CYCLE, 24, TimeUnit.HOURS),
                buildCache(Constants.STATES, 24, TimeUnit.HOURS),
                buildCache(Constants.CITIES, 24, TimeUnit.HOURS),
                buildCache(Constants.COURSES, 24, TimeUnit.HOURS),

                // Statistics caches - expire after 1 hour (change more frequently)
                buildCache(Constants.GENERAL_STATISTICS, 1, TimeUnit.HOURS),
                buildCache(Constants.STATE_STATISTICS, 1, TimeUnit.HOURS),
                buildCache(Constants.MUNICIPALITY_STATISTICS, 1, TimeUnit.HOURS),
                buildCache(Constants.COLLEGE_STATISTICS, 1, TimeUnit.HOURS),
                buildCache(Constants.GRADE_STATISTICS, 1, TimeUnit.HOURS),
                buildCache(Constants.STUDENT_STATISTICS, 30, TimeUnit.MINUTES),

                // Evidence caches - expire after 1 hour
                buildCache(Constants.EVIDENCE_CHECK, 1, TimeUnit.HOURS)
        ));
        return cacheManager;
    }

    private CaffeineCache buildCache(String name, long duration, TimeUnit timeUnit) {
        return new CaffeineCache(name, Caffeine.newBuilder()
                .expireAfterWrite(duration, timeUnit)
                .maximumSize(1000)
                .recordStats()
                .build());
    }
}
