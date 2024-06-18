package com.hslcreator.LibraryAPI.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@AutoConfiguration
@ComponentScan
@Order(Integer.MIN_VALUE)
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowedHeaders("*")
                .maxAge(3600);
    }

    @Bean
    public CorsConfigurationSource commonModuleConfigurationSource() {
        CorsConfiguration corsConfig = new CorsConfiguration();
//        corsConfig.setAllowedOrigins(List.of("*")); // Allow all origins
//        corsConfig.setAllowedMethods(List.of("*")); // Allow all HTTP methods
//        corsConfig.setAllowedHeaders(List.of("*")); // Allow all headers
//        corsConfig.setAllowCredentials(true);
        corsConfig.applyPermitDefaultValues();
        corsConfig.setAllowCredentials(true);

        // You can customize other CORS options here as needed

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfig);
        return source;
    }


}
