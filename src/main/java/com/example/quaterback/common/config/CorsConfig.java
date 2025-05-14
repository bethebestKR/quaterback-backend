package com.example.quaterback.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")        // 모든 경로에 대해
                        .allowedOrigins("*")      // 모든 Origin 허용
                        .allowedMethods("*")      // 모든 HTTP Method(GET, POST, PUT, DELETE 등) 허용
                        .allowedHeaders("*")      // 모든 헤더 허용
                        .allowCredentials(false); // 인증정보 포함할지 (true면 Origin * 못씀 주의)
            }
        };
    }
}