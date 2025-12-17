package com.eventpro.catering. config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;

/**
 * CONFIGURAZIONE CORS PER INTEGRAZIONE FRONTEND-BACKEND
 *
 * Permette al frontend (localhost:5173) di chiamare il backend (localhost:8080)
 * senza errori CORS (Cross-Origin Resource Sharing).
 */
@Configuration
public class CorsConfig {

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        // ✅ ORIGINI PERMESSE (Frontend)
        configuration.setAllowedOrigins(Arrays.asList(
                "http://localhost:5173",  // Vite dev server
                "http://localhost:3000",  // React/CRA (backup)
                "http://127.0.0.1:5173"   // Alternativa localhost
        ));

        // ✅ METODI HTTP PERMESSI
        configuration.setAllowedMethods(Arrays. asList(
                "GET",
                "POST",
                "PUT",
                "DELETE",
                "OPTIONS",
                "PATCH"
        ));

        // ✅ HEADERS PERMESSI
        configuration.setAllowedHeaders(Arrays.asList(
                "Authorization",      // Per JWT token
                "Content-Type",       // Per JSON
                "Accept",
                "Origin",
                "Access-Control-Request-Method",
                "Access-Control-Request-Headers"
        ));

        // ✅ PERMETTI CREDENZIALI (cookies, auth headers)
        configuration.setAllowCredentials(true);

        // ✅ ESPONI HEADERS NELLA RISPOSTA
        configuration.setExposedHeaders(Arrays.asList(
                "Authorization",
                "Access-Control-Allow-Origin",
                "Access-Control-Allow-Credentials"
        ));

        // ✅ CACHE PREFLIGHT REQUESTS (1 ora)
        configuration.setMaxAge(3600L);

        // ✅ APPLICA A TUTTI GLI ENDPOINT
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }

    @Bean
    public CorsFilter corsFilter() {
        return new CorsFilter(corsConfigurationSource());
    }
}