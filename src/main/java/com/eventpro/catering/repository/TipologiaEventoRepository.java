package com.eventpro.catering.repository;

import com.eventpro.catering.model.TipologiaEvento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * REPOSITORY TIPOLOGIA_EVENTO - INTERFACE PER ACCEDERE AI DATI DELLE TIPOLOGIE
 *
 * Un Repository è un'interfaccia che Spring usa per gestire l'accesso ai dati.
 * Invece di scrivere query SQL manualmente, il Repository fa tutto automaticamente.
 *
 * COME FUNZIONA:
 * 1. Estendi JpaRepository<TipologiaEvento, Long>
 *    - TipologiaEvento: è la classe entity che gestisci
 *    - Long: è il tipo dell'ID (la chiave primaria)
 * 2. JpaRepository ti fornisce automaticamente metodi come:
 *    - save(tipologia): salva una tipologia nel database
 *    - findById(id): trova una tipologia per ID
 *    - findAll(): restituisce tutte le tipologie
 *    - delete(tipologia): elimina una tipologia
 *    - count(): conta quante tipologie ci sono
 * 3. Non devi implementare nulla, Spring lo fa per te!
 *
 * PERCHÉ SERVE @Repository:
 * - È un'annotazione che dice a Spring di gestire questa interfaccia
 * - Spring crea automaticamente un'implementazione del Repository
 * - Puoi poi usarlo in altre classi (Service, Controller) tramite l'iniezione di dipendenze
 */
@Repository
public interface TipologiaEventoRepository extends JpaRepository<TipologiaEvento, Long> {

    /**
     * TROVA UNA TIPOLOGIA USANDO IL NOME
     *
     * Usa la convenzione di denominazione di Spring Data JPA per creare automaticamente
     * una query SQL che cerca una tipologia in base al suo nome.
     *
     * QUERY SQL GENERATA AUTOMATICAMENTE:
     * SELECT * FROM TIPOLOGIA_EVENTO WHERE nome = ?
     *
     * COSA RESTITUISCE:
     * - Optional<TipologiaEvento>: un oggetto "Optional" che potrebbe contenere o meno una "TipologiaEvento"
     * - Facile da gestire: evita i "NullPointerException"
     *
     * ESEMPIO DI USO:
     * Optional<TipologiaEvento> tipologia = tipologiaEventoRepository.findByNome("Matrimonio");
     * if (tipologia.isPresent()) {
     *     // la tipologia esiste, puoi accedervi con tipologia.get()
     *     System.out.println("Trovata: " + tipologia.get().getNome());
     * } else {
     *     System.out.println("Tipologia non trovata");
     * }
     *
     * @param nome Il nome della tipologia da cercare
     * @return Un Optional che contiene la tipologia trovata (se esiste), o vuoto
     */
    Optional<TipologiaEvento> findByNome(String nome);

    /**
     * VERIFICA SE ESISTE UNA TIPOLOGIA CON UN DETERMINATO NOME
     *
     * Usa la convenzione di denominazione di Spring Data JPA per creare automaticamente
     * una query SQL che controlla se una tipologia con un certo nome esiste.
     *
     * QUERY SQL GENERATA AUTOMATICAMENTE:
     * SELECT COUNT(*) > 0 FROM TIPOLOGIA_EVENTO WHERE nome = ?
     *
     * COSA RESTITUISCE:
     * - boolean: `true` se la tipologia esiste, altrimenti `false`
     *
     * ESEMPIO DI USO:
     * boolean esiste = tipologiaEventoRepository.existsByNome("Matrimonio");
     * if (esiste) {
     *     System.out.println("La tipologia 'Matrimonio' esiste già");
     * } else {
     *     System.out.println("Puoi creare la tipologia 'Matrimonio'");
     * }
     *
     * UTILE PER:
     * - Validazione prima di creare una nuova tipologia
     * - Evitare duplicati (il nome è unique nel database)
     *
     * @param nome Il nome da verificare
     * @return `true` se una tipologia esiste con questo nome, altrimenti `false`
     */
    boolean existsByNome(String nome);

    // I metodi di JpaRepository sono già disponibili automaticamente:
    // - save(tipologia) -> salva o aggiorna
    // - findById(id) -> trova per ID
    // - findAll() -> lista tutte le tipologie
    // - deleteById(id) -> elimina per ID
    // - delete(tipologia) -> elimina l'oggetto
    // - count() -> conta le tipologie totali
}
