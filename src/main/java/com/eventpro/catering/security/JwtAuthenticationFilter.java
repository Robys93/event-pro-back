package com.eventpro.catering.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;
import org. springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core. context.SecurityContextHolder;
import org.springframework.security.core. userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * JWT AUTHENTICATION FILTER
 *
 * Questo filtro intercetta TUTTE le richieste HTTP e verifica se c'è un token JWT valido.
 *
 * FLUSSO:
 * 1. Estrae l'header "Authorization" dalla richiesta HTTP
 * 2. Verifica che inizi con "Bearer"
 * 3. Estrae il token JWT (rimuovendo "Bearer")
 * 4. Usa JwtService per estrarre l'username (email) dal token
 * 5. Carica l'utente dal database usando CustomUserDetailsService
 * 6. Valida il token
 * 7. Se tutto è ok, setta l'utente nel SecurityContext (così Spring Security sa chi è l'utente loggato)
 *
 * QUANDO VIENE ESEGUITO:
 * - Prima di ogni richiesta HTTP (tranne /api/auth/** che è pubblico)
 * - OncePerRequestFilter garantisce che venga eseguito UNA SOLA VOLTA per richiesta
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    // Costruttore per dependency injection
    public JwtAuthenticationFilter(JwtService jwtService, UserDetailsService userDetailsService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    /**
     * METODO PRINCIPALE DEL FILTRO
     *
     * Viene chiamato automaticamente da Spring per ogni richiesta HTTP.
     *
     * @param request La richiesta HTTP in arrivo
     * @param response La risposta HTTP da inviare
     * @param filterChain La catena di filtri (per passare al prossimo filtro)
     */
    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {

        // STEP 1: Estrae l'header "Authorization" dalla richiesta
        // Esempio: "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9..."
        final String authHeader = request.getHeader("Authorization");

        // STEP 2: Verifica se l'header esiste e inizia con "Bearer "
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            // Se non c'è token, passa al prossimo filtro senza autenticare
            filterChain.doFilter(request, response);
            return;
        }

        // STEP 3: Estrae il token JWT (rimuove "Bearer " dall'inizio)
        // Esempio: "Bearer eyJhbGciOiJIUzI1NiJ9..." → "eyJhbGciOiJIUzI1NiJ9..."
        final String jwt = authHeader.substring(7);

        // STEP 4: Estrae l'username (email) dal token usando JwtService
        final String userEmail = jwtService.extractUsername(jwt);

        // STEP 5: Verifica che l'email non sia null e che l'utente non sia già autenticato
        // SecurityContextHolder.getContext().getAuthentication() == null significa che l'utente non è ancora loggato
        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            // STEP 6: Carica i dettagli dell'utente dal database
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);

            // STEP 7: Valida il token JWT
            // Verifica che il token non sia scaduto e che l'username corrisponda
            if (jwtService.validateToken(jwt, userDetails)) {

                // STEP 8: Crea un oggetto di autenticazione per Spring Security
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,              // L'utente autenticato
                        null,                     // Le credenziali (non servono qui, il token è già validato)
                        userDetails. getAuthorities() // I ruoli/permessi dell'utente
                );

                // STEP 9: Aggiungi dettagli aggiuntivi sulla richiesta (IP, session, ecc.)
                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );

                // STEP 10: Setta l'utente nel SecurityContext
                // Da questo momento in poi, Spring Security sa che l'utente è autenticato
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        // STEP 11: Passa al prossimo filtro nella catena
        filterChain. doFilter(request, response);
    }
}