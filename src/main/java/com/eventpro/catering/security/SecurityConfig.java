package com.eventpro.catering.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.http.SessionCreationPolicy;

/**
 * SECURITY CONFIG
 *
 * Configura Spring Security per proteggere le API della tua applicazione.
 * - Concede accesso pubblico agli endpoint di autenticazione (/api/auth/**)
 * - Richiede autenticazione per tutto il resto
 * - Disabilita CSRF (per semplicità) e configura SESSION_STATELESS (ideale per API REST)
 */
@Configuration
@EnableMethodSecurity // Utile per autorizzazioni future basate su @PreAuthorize
public class SecurityConfig {

    /**
     * CONFIGURA PasswordEncoder
     *
     * BCryptPasswordEncoder è il metodo standard per hash sicuri e gestione delle password.
     *
     * @return BCryptPasswordEncoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * CONFIGURA AuthenticationManager
     *
     * AuthenticationManager è una componente fondamentale di Spring Security.
     * Spring inietta un'implementazione automatica tramite AuthenticationConfiguration.
     *
     * @param authenticationConfiguration Configurazione auto-gestita da Spring
     * @return AuthenticationManager
     * @throws Exception In caso di errori durante l'estrazione
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    /**
     * CONFIGURA SecurityFilterChain
     *
     * Definisce come le richieste HTTP devono essere protette.
     * - Disabilita CSRF (per semplicità, dato che usiamo JSON nelle richieste REST).
     * - Configura SESSION_STATELESS (adatto per API REST, senza memorizzare sessioni Server-Side).
     * - Permette accesso pubblico alle rotte /api/auth/**
     * - Richiede autenticazione per tutte le altre rotte.
     *
     * @param http Oggetto HttpSecurity fornito da Spring
     * @return SecurityFilterChain
     * @throws Exception In caso di configurazione errata
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Configura CSRF utilizzando il metodo aggiornato
                .csrf(AbstractHttpConfigurer::disable)

                // Imposta la gestione della sessione in modalità STATELESS (API REST)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // Configura i permessi per gli endpoint
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/**").permitAll() // Accesso pubblico agli endpoint di autenticazione
                        .anyRequest().authenticated() // Protegge tutto il resto
                );

        return http.build();
    }
}