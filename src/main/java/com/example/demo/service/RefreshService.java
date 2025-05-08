package com.example.demo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.context.refresh.ContextRefresher;
import org.springframework.stereotype.Service;
import java.util.Set;

@Service
public class RefreshService {

    private static final Logger logger = LoggerFactory.getLogger(RefreshService.class);

    private final ContextRefresher contextRefresher;

    public RefreshService(ContextRefresher contextRefresher) {
        this.contextRefresher = contextRefresher;
    }

    public Set<String> refreshProperties() {
        logger.info("Properties refresh requested.");
        // In a real scenario, you might want to compare property sources before and after
        // to determine exactly which keys were changed. ContextRefresher.refresh() returns a Set of changed keys.
        Set<String> refreshedKeys = null;
        try {
            // For Spring Boot 2.x, ContextRefresher.refresh() reloads the configuration
            // and re-binds @ConfigurationProperties beans.
            // It returns a collection of configuration keys that were changed.
            refreshedKeys = this.contextRefresher.refresh();
            if (refreshedKeys == null || refreshedKeys.isEmpty()) {
                logger.info("Properties refreshed successfully, but no specific keys were reported as changed by the refresher.");
            } else {
                logger.info("Properties refreshed successfully. Refreshed keys: {}", refreshedKeys);
            }
            return refreshedKeys;
        } catch (Exception e) {
            logger.error("Failed to refresh properties: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to refresh properties: " + e.getMessage(), e);
        }
    }
}
