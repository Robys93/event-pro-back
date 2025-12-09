package com.eventpro.catering.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * JWT SERVICE - GESTIONE TOKEN JWT
 *
 * Questo service gestisce tutte le operazioni relative ai token JWT:
 * - Generazione di nuovi token
 * - Estrazione delle informazioni dai token
 * - Validazione dei token
 *
 * JWT (JSON Web Token) è un metodo standard per trasmettere informazioni in modo sicuro.
 * Un token JWT è composto da 3 parti separate da punti:
 * Header.Payload.Signature
 *
 * Esempio: eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyQGV4YW1wbGUuY29tIn0.xyz123
 */
@Service
public class JwtService {

    // Secret key dal file application.properties
    @Value("${jwt.secret}")
    private String secretKey;

    // Durata del token in millisecondi dal file application.properties
    @Value("${jwt.expiration}")
    private long jwtExpiration;

    /**
     * GENERA UN TOKEN JWT PER UN UTENTE
     *
     * Crea un nuovo token JWT contenente:
     * - Username (email) come "subject"
     * - Data di creazione
     * - Data di scadenza
     * - Firma digitale con la secret key
     *
     * @param username L'email dell'utente (usata come identificatore univoco)
     * @return Il token JWT generato come stringa
     */
    public String generateToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, username);
    }

    /**
     * GENERA UN TOKEN JWT CON CLAIMS PERSONALIZZATI
     *
     * Permette di aggiungere informazioni extra nel token (claims).
     * I claims sono coppie chiave-valore che possono contenere dati aggiuntivi.
     *
     * @param extraClaims Mappa con informazioni aggiuntive da includere nel token
     * @param username L'email dell'utente
     * @return Il token JWT generato
     */
    public String generateToken(Map<String, Object> extraClaims, String username) {
        return createToken(extraClaims, username);
    }

    /**
     * CREA EFFETTIVAMENTE IL TOKEN JWT
     *
     * Metodo privato che costruisce il token usando la libreria JJWT.
     *
     * @param claims Informazioni da includere nel token
     * @param subject L'identificatore dell'utente (email)
     * @return Il token JWT firmato
     */
    private String createToken(Map<String, Object> claims, String subject) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpiration);

        return Jwts.builder()
                .claims(claims)                             // Informazioni personalizzate
                .subject(subject)                           // Username (email)
                .issuedAt(now)                             // Data di creazione
                .expiration(expiryDate)                     // Data di scadenza
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)  // Firma con algoritmo HS256
                .compact();                                 // Genera la stringa finale
    }

    /**
     * ESTRAE L'USERNAME (EMAIL) DAL TOKEN
     *
     * Decodifica il token e restituisce lo username (che per noi è l'email).
     * Questo metodo è fondamentale per identificare chi ha fatto la richiesta.
     *
     * @param token Il token JWT da cui estrarre l'username
     * @return L'email dell'utente
     */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * ESTRAE LA DATA DI SCADENZA DAL TOKEN
     *
     * @param token Il token JWT
     * @return La data di scadenza del token
     */
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * ESTRAE UN CLAIM SPECIFICO DAL TOKEN
     *
     * Metodo generico per estrarre qualsiasi informazione dal token.
     * Usa una Function per specificare quale claim estrarre.
     *
     * @param token Il token JWT
     * @param claimsResolver Funzione che specifica quale claim estrarre
     * @return Il valore del claim richiesto
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * ESTRAE TUTTI I CLAIMS DAL TOKEN
     *
     * Decodifica il token e restituisce tutti i claims contenuti.
     * Verifica anche la firma del token usando la secret key.
     *
     * AGGIORNATO per JJWT 0.12.x: usa parser() invece di parserBuilder()
     *
     * @param token Il token JWT
     * @return Oggetto Claims con tutte le informazioni del token
     */
    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSignInKey())         // Verifica la firma con la chiave
                .build()
                .parseSignedClaims(token)           // Parsifica e valida il token
                .getPayload();                      // Restituisce i claims (payload)
    }

    /**
     * VERIFICA SE IL TOKEN È SCADUTO
     *
     * @param token Il token JWT
     * @return true se il token è scaduto, false altrimenti
     */
    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /**
     * VALIDA IL TOKEN JWT
     *
     * Verifica che:
     * 1. L'username nel token corrisponda a quello dell'utente
     * 2. Il token non sia scaduto
     *
     * @param token Il token JWT da validare
     * @param userDetails I dettagli dell'utente dal database
     * @return true se il token è valido, false altrimenti
     */
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    /**
     * CONVERTE LA SECRET KEY IN UN OGGETTO SecretKey
     *
     * La secret key è una stringa, ma JJWT 0.12.x ha bisogno di un oggetto SecretKey.
     * Questo metodo fa la conversione usando l'algoritmo HMAC-SHA256.
     *
     * @return Oggetto SecretKey per firmare/verificare i token
     */
    private SecretKey getSignInKey() {
        byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
