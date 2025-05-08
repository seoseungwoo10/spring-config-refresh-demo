package com.example.demo.controller;

import com.example.demo.config.FeatureXConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class TestController {

    private final FeatureXConfig featureXConfig;

    @Value("${app.message}")
    private String appMessage;

    public TestController(FeatureXConfig featureXConfig) {
        this.featureXConfig = featureXConfig;
    }

    @GetMapping("/test/config")
    public Map<String, Object> getCurrentConfig() {
        Map<String, Object> configMap = new HashMap<>();
        configMap.put("app.message", appMessage);
        configMap.put("app.feature.x.enabled", featureXConfig.isEnabled());
        configMap.put("app.feature.x.name", featureXConfig.getName());
        return configMap;
    }
}
