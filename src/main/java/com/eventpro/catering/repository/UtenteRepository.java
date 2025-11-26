package com.eventpro.catering.repository;

import com. eventpro.catering.model.Utente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

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

    // I metodi di JpaRepository sono già disponibili (save, findAll, ecc.)
    // Se vuoi aggiungere query personalizzate, le scrivi qui
    // Esempio:
    // Utente findByUsername(String username);
}