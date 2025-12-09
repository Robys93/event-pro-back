package com.eventpro.catering.controller;

import com.eventpro.catering.dto.AuthResponse;
import com.eventpro.catering.dto.LoginRequest;
import com.eventpro.catering.model.Utente;
import com.eventpro.catering.repository.UtenteRepository;
import com.eventpro.catering.security.JwtService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

/**
 * CONTROLLER PER AUTENTICAZIONE E REGISTRAZIONE
 *
 * Gestisce gli endpoint per:
 * - Registrazione nuovi utenti
 * - Login utenti esistenti (con generazione JWT)
 *
 * Tutti gli endpoint sono sotto il path /api/auth
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UtenteRepository utenteRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;  // AGGIUNTO: Service per gestire JWT

    // Costruttore per dependency injection
    public AuthController(UtenteRepository utenteRepository,
                          PasswordEncoder passwordEncoder,
                          AuthenticationManager authenticationManager,
                          JwtService jwtService) {  // AGGIUNTO: Inietta JwtService
        this.utenteRepository = utenteRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    /**
     * ENDPOINT PER LA REGISTRAZIONE
     *
     * POST /api/auth/register
     *
     * Riceve i dati dell'utente, cripta la password con BCrypt
     * e salva l'utente nel database.
     *
     * @param utente I dati dell'utente da registrare (email, password, nome, cognome, role)
     * @return ResponseEntity con messaggio di successo o errore
     */
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Utente utente) {
        try {
            // Verifica se l'email esiste già
            if (utenteRepository.existsByEmail(utente.getEmail())) {
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body(new AuthResponse("Email già registrata"));
            }

            // Cripta la password con BCrypt prima di salvare
            String passwordCriptata = passwordEncoder.encode(utente.getPassword());
            utente.setPassword(passwordCriptata);

            // Se il ruolo non è specificato, assegna un ruolo di default
            if (utente.getRole() == null || utente.getRole().isEmpty()) {
                utente.setRole("USER");
            }

            // Salva l'utente nel database
            Utente utenteRegistrato = utenteRepository.save(utente);

            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(new AuthResponse("Registrazione completata con successo"));

        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new AuthResponse("Errore durante la registrazione: " + e.getMessage()));
        }
    }

    /**
     * ENDPOINT PER IL LOGIN CON JWT
     *
     * POST /api/auth/login
     *
     * MODIFICATO: Ora genera e restituisce un token JWT invece di un semplice messaggio.
     *
     * Riceve email e password, verifica le credenziali usando AuthenticationManager.
     * Se le credenziali sono corrette, genera un token JWT e lo restituisce.
     *
     * FLUSSO:
     * 1. Riceve LoginRequest con email e password
     * 2. Crea un token di autenticazione
     * 3. AuthenticationManager verifica le credenziali:
     *    - Chiama CustomUserDetailsService per recuperare l'utente
     *    - Confronta la password inserita con quella criptata nel DB usando BCrypt
     * 4. Se tutto ok, genera un token JWT usando JwtService
     * 5. Restituisce il token nel campo "accessToken" dell'AuthResponse
     * 6. Se le credenziali sono sbagliate, lancia AuthenticationException
     *
     * @param loginRequest DTO con email e password
     * @return ResponseEntity con token JWT o messaggio di errore
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            // Crea un token di autenticazione con email e password
            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getEmail(),
                            loginRequest.getPassword()
                    );

            // AuthenticationManager verifica le credenziali
            // Se le credenziali sono sbagliate, lancia un'eccezione
            Authentication authentication = authenticationManager.authenticate(authToken);

            // MODIFICATO: Se arriviamo qui, le credenziali sono corrette
            // Genera il token JWT usando l'email dell'utente
            String jwtToken = jwtService.generateToken(loginRequest.getEmail());

            // MODIFICATO: Restituisce il token JWT nell'AuthResponse
            return ResponseEntity.ok(
                    new AuthResponse("Login effettuato con successo", jwtToken)
            );

        } catch (AuthenticationException e) {
            // Credenziali sbagliate
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(new AuthResponse("Email o password non corretti"));
        } catch (Exception e) {
            // Altri errori
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new AuthResponse("Errore durante il login: " + e.getMessage()));
        }
    }
}
