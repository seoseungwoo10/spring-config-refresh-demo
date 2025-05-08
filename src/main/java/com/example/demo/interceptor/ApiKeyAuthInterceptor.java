package com.example.demo.interceptor;

import com.example.demo.config.AdminSecurityConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull; // Import NonNull
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class ApiKeyAuthInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(ApiKeyAuthInterceptor.class);
    private static final String API_KEY_HEADER = "X-API-KEY";
    private final AdminSecurityConfig adminSecurityConfig;

    public ApiKeyAuthInterceptor(AdminSecurityConfig adminSecurityConfig) {
        this.adminSecurityConfig = adminSecurityConfig;
    }

    @Override
    public boolean preHandle(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler) throws Exception {
        if (request.getRequestURI().startsWith("/admin/refresh")) {
            String apiKey = request.getHeader(API_KEY_HEADER);
            String maskedApiKey = apiKey != null && !apiKey.isEmpty() ? "******" + apiKey.substring(Math.max(0, apiKey.length() - 4)) : "[empty]";
            if (apiKey == null || apiKey.isEmpty()) {
                logger.warn("Missing API Key in request to {}. X-API-KEY: {}", request.getRequestURI(), maskedApiKey);
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Missing API Key");
                return false;
            }
            if (!apiKey.equals(adminSecurityConfig.getApiKey())) {
                // Log the received masked API key for easier debugging of key mismatches
                logger.warn("Invalid API Key provided for request to {}. X-API-KEY: {}", request.getRequestURI(), maskedApiKey);
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid API Key");
                return false;
            }
            logger.info("Valid API Key received for request to {}. X-API-KEY: {}", request.getRequestURI(), maskedApiKey);
        }
        return true;
    }
}
