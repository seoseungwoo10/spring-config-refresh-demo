package com.example.demo.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope; // Import RefreshScope
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "app.feature.x")
@RefreshScope // Add RefreshScope to make this bean refreshable
public class FeatureXConfig {

    private boolean enabled;
    private String name;

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
