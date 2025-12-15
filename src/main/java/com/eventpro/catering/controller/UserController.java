package com.eventpro.catering.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * USER CONTROLLER
 *
 * Controller per gli endpoint relativi all'utente autenticato.
 *
 * IMPORTANTE:
 * - Questo controller NON è sotto /api/auth/**, quindi è PROTETTO
 * - Serve un token JWT valido per accedere a questi endpoint
 * - Usato per testare che l'autenticazione JWT funzioni correttamente
 */
@RestController
@RequestMapping("/api/user")
public class UserController {

    /**
     * ENDPOINT: GET /api/user/me
     *
     * Restituisce le informazioni dell'utente attualmente autenticato.
     *
     * COME FUNZIONA:
     * 1. Il client invia una richiesta con header:  Authorization: Bearer <token>
     * 2. Il JwtAuthenticationFilter intercetta la richiesta
     * 3. Il filtro valida il token e setta l'utente nel SecurityContext
     * 4. Questo metodo legge l'utente dal SecurityContext
     * 5. Restituisce l'email dell'utente
     *
     * FLUSSO DI TEST:
     * 1. Fai login: POST /api/auth/login → ricevi il token
     * 2. Chiama questo endpoint: GET /api/user/me con header Authorization: Bearer <token>
     * 3. Se tutto funziona, ricevi i dati dell'utente
     *
     * ESEMPIO DI RISPOSTA:
     * {
     *   "email": "test@example.com",
     *   "message": "Utente autenticato con successo"
     * }
     *
     * @return ResponseEntity con i dati dell'utente
     */
    @GetMapping("/me")
    public ResponseEntity<? > getCurrentUser() {
        // Recupera l'utente autenticato dal SecurityContext
        // SecurityContextHolder contiene le informazioni sull'utente loggato
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Verifica che l'utente sia effettivamente autenticato
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(401).body("Utente non autenticato");
        }

        // Estrae l'email dell'utente (che è lo "username" in Spring Security)
        String email = authentication.getName();

        // Costruisce la risposta JSON
        Map<String, String> response = new HashMap<>();
        response.put("email", email);
        response.put("message", "Utente autenticato con successo");

        // Restituisce la risposta
        return ResponseEntity.ok(response);
    }
}