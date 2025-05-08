package com.example.demo.controller;

import com.example.demo.service.ConfigUpdateService;
import com.example.demo.service.RefreshService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@RestController
public class AdminController {

    private static final Logger log = LoggerFactory.getLogger(AdminController.class);
    private final RefreshService refreshService;
    private final ConfigUpdateService configRefreshService;

    public AdminController(RefreshService refreshService, ConfigUpdateService configRefreshService) {
        this.refreshService = refreshService;
        this.configRefreshService = configRefreshService;
    }

    @PostMapping("/admin/refresh/properties")
    public ResponseEntity<Map<String, Object>> refreshProperties(@RequestHeader("X-API-KEY") String apiKey) {
        // API Key validation is handled by ApiKeyAuthInterceptor
        // Logging of API key presence/validity is also handled there.
        log.info("[RefreshController] Received request to refresh properties.");

        Map<String, Object> response = new HashMap<>();
        try {
            Set<String> refreshedKeys = refreshService.refreshProperties();
            response.put("status", "success");
            response.put("message", "Properties refreshed successfully.");
            response.put("refreshedKeys", refreshedKeys != null ? refreshedKeys : Collections.emptyList());
            // Specific logging of refreshed keys is done in RefreshService
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("[RefreshController] Failed to refresh properties: {}", e.getMessage(), e);
            response.put("status", "error");
            response.put("message", "Failed to refresh properties: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PostMapping("/admin/refresh/custom-config")
    public ResponseEntity<String> refreshCustomConfig(@RequestHeader("X-API-KEY") String apiKey,
                                                      @RequestBody String newConfig) {

        log.info("[RefreshController] Received request to refresh update properties.");

        try {
            configRefreshService.refreshCustomConfig(newConfig);
            return ResponseEntity.ok("custom-config.yml 설정이 성공적으로 갱신되었습니다.");
        } catch (IOException e) {
            log.error("커스텀 설정 파일 갱신 중 오류 발생", e);
            return ResponseEntity.internalServerError().body("설정 갱신 중 오류 발생: " + e.getMessage());
        }
    }
}
