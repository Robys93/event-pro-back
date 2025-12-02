package com.eventpro.catering.repository;

import com.eventpro.catering.model.Utente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * REPOSITORY UTENTE - INTERFACE PER ACCEDERE AI DATI DEGLI UTENTI
 *
 * Un Repository è un'interfaccia che Spring usa per gestire l'accesso ai dati.
 * Invece di scrivere query SQL manualmente, il Repository fa tutto automaticamente.
 *
 * COME FUNZIONA:
 * 1.  Estendi JpaRepository<Utente, Long>
 *    - Utente: è la classe che gestisci
 *    - Long: è il tipo dell'ID (la chiave primaria)
 * 2. JpaRepository ti fornisce automaticamente metodi come:
 *    - save(utente): salva un utente nel database
 *    - findById(id): trova un utente per ID
 *    - findAll(): restituisce tutti gli utenti
 *    - delete(utente): elimina un utente
 * 3. Non devi implementare nulla, Spring lo fa per te!
 *
 * PERCHÉ SERVE @Repository:
 * - È un'annotazione che dice a Spring di gestire questa interfaccia
 * - Spring crea automaticamente un'implementazione del Repository
 * - Puoi poi usarlo in altre classi (Service, Controller) tramite l'iniezione di dipendenze
 */
@Repository
public interface UtenteRepository extends JpaRepository<Utente, Long> {

    /**
     * TROVA UN UTENTE USANDO L'EMAIL
     *
     * Usa la convenzione di denominazione di Spring Data JPA per creare automaticamente
     * una query SQL che cerca un utente in base alla sua email.
     *
     * COSA RESTITUISCE:
     * - Optional<Utente>: un oggetto "Optional" che potrebbe contenere o meno un "Utente"
     * - Facile da gestire: evita i "NullPointerException"
     *
     * ESEMPIO DI USO:
     * Optional<Utente> utente = utenteRepository.findByEmail("test@example.com");
     * if (utente.isPresent()) {
     *     // l'utente esiste, puoi accedervi con utente.get();
     * }
     *
     * @param email L'email dell'utente da cercare
     * @return Un Optional che contiene l'utente trovato (se esiste), o vuoto
     */
    Optional<Utente> findByEmail(String email);

    /**
     * VERIFICA SE ESISTE UN UTENTE CON UNA DETERMINATA EMAIL
     *
     * Usa la convenzione di denominazione di Spring Data JPA per creare automaticamente
     * una query SQL che controlla se un utente con una certa email esiste.
     *
     * COSA RESTITUISCE:
     * - boolean: `true` se l'utente esiste, altrimenti `false`
     *
     * ESEMPIO DI USO:
     * boolean esiste = utenteRepository.existsByEmail("test@example.com");
     * if (esiste) {
     *     // esiste già un utente con questa email
     * }
     *
     * @param email L'email da verificare
     * @return `true` se un utente esiste con questa email, altrimenti `false`
     */
    boolean existsByEmail(String email);

    // I metodi di JpaRepository sono già disponibili (save, findAll, ecc.)

}