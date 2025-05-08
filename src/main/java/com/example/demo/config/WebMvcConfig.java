package com.example.demo.config;

import com.example.demo.interceptor.ApiKeyAuthInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull; // Import NonNull
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    private final ApiKeyAuthInterceptor apiKeyAuthInterceptor;

    public WebMvcConfig(ApiKeyAuthInterceptor apiKeyAuthInterceptor) {
        this.apiKeyAuthInterceptor = apiKeyAuthInterceptor;
    }

    @Override
    public void addInterceptors(@NonNull InterceptorRegistry registry) { // Add @NonNull
        registry.addInterceptor(apiKeyAuthInterceptor).addPathPatterns("/admin/refresh/properties");
    }
}
