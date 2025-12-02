package com.eventpro.catering.security;

import com.eventpro.catering.model.Utente;
import com.eventpro.catering.repository.UtenteRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * CUSTOM USER DETAILS SERVICE
 *
 * Questo service implementa UserDetailsService di Spring Security.
 * Il suo compito è dire a Spring Security come recuperare un utente dal database.
 *
 * FLUSSO:
 * 1. Quando un utente tenta il login, Spring Security chiama loadUserByUsername(email)
 * 2. Questo metodo cerca l'utente nel database usando UtenteRepository
 * 3. Se trovato, costruisce un oggetto UserDetails con email, password e ruoli
 * 4. Spring Security usa questo oggetto per verificare le credenziali
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UtenteRepository utenteRepository;

    // Costruttore per dependency injection
    public CustomUserDetailsService(UtenteRepository utenteRepository) {
        this.utenteRepository = utenteRepository;
    }

    /**
     * CARICA L'UTENTE DAL DATABASE
     *
     * Questo metodo viene chiamato da Spring Security durante il login.
     * Per noi, "username" è l'email dell'utente.
     *
     * @param username L'email dell'utente (chiamata "username" per convenzione Spring Security)
     * @return UserDetails con i dati dell'utente
     * @throws UsernameNotFoundException Se l'utente non viene trovato
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Cerca l'utente nel database usando l'email
        Utente utente = utenteRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException(
                        "Utente non trovato con email: " + username
                ));

        // Costruisce la lista dei ruoli/permessi
        List<GrantedAuthority> authorities = new ArrayList<>();

        // Aggiungi il ruolo dell'utente
        // Se il campo role è null, assegna un ruolo di default
        String role = utente.getRole() != null ? utente.getRole() : "USER";

        // Spring Security richiede che i ruoli inizino con "ROLE_"
        if (!role.startsWith("ROLE_")) {
            role = "ROLE_" + role;
        }

        authorities.add(new SimpleGrantedAuthority(role));

        // Costruisce e restituisce l'oggetto UserDetails
        // Spring Security usa questo per verificare email e password
        return User.builder()
                .username(utente.getEmail())           // Email come username
                .password(utente.getPassword())         // Password criptata dal DB
                .authorities(authorities)               // Ruoli/permessi
                .accountExpired(false)                  // Account non scaduto
                .accountLocked(false)                   // Account non bloccato
                .credentialsExpired(false)              // Credenziali non scadute
                .disabled(false)                        // Account abilitato
                .build();
    }
}
