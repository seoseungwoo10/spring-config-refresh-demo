package com.example.demo.service;

import com.example.demo.controller.AdminController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;
@Service
public class ConfigUpdateService {
    private static final Logger log = LoggerFactory.getLogger(ConfigUpdateService.class);

    public void refreshCustomConfig(String newContent) throws IOException {
        // 새 설정을 파일에 저장
        updateConfigFile("custom-config", newContent);
    }
    private void updateConfigFile(String configName, String newContent) throws IOException {
        Path configPath = Paths.get("src/main/resources/" + configName + ".yml");
        Files.write(configPath, newContent.getBytes());
        log.info("Updated {} configuration file", configName);
    }
}
