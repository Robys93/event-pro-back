package com.eventpro.catering.model;

import jakarta.persistence.*;

/**
 * ENTITY TIPOLOGIA_EVENTO - RAPPRESENTA UNA TIPOLOGIA DI EVENTO
 *
 * Un Entity è una classe Java che rappresenta una tabella nel database.
 * Questa entità classifica i tipi di eventi gestiti nel sistema.
 *
 * ESEMPI DI TIPOLOGIE:
 * - Matrimonio
 * - Compleanno
 * - Evento aziendale
 * - Conferenza
 * - Festa privata
 *
 * COSA SUCCEDE:
 * 1. Spring crea automaticamente una tabella "TIPOLOGIA_EVENTO" nel database
 * 2. Ogni istanza di TipologiaEvento corrisponde a una riga della tabella
 * 3. Gli eventi possono essere collegati a una tipologia specifica
 *
 * ANNOTAZIONI USATE:
 * - @Entity: dice a Hibernate che questa classe rappresenta una tabella
 * - @Table: specifica il nome della tabella nel database
 * - @Id: specifica la colonna che è la chiave primaria
 * - @GeneratedValue: genera automaticamente l'ID
 * - @Column: configura le proprietà delle colonne
 */
@Entity
@Table(name = "TIPOLOGIA_EVENTO")
public class TipologiaEvento {

    /**
     * ID UNIVOCO DELLA TIPOLOGIA
     *
     * @Id: questa colonna è la chiave primaria (ogni tipologia ha un ID unico)
     * @GeneratedValue(strategy = GenerationType.IDENTITY): genera automaticamente l'ID
     *     - IDENTITY: il database genera l'ID automaticamente (1, 2, 3, ...)
     *
     * ESEMPIO:
     * - Prima tipologia creata: ID = 1 (es. "Matrimonio")
     * - Seconda tipologia creata: ID = 2 (es. "Compleanno")
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * NOME DELLA TIPOLOGIA
     *
     * @Column(nullable = false, unique = true):
     *     - nullable = false: il nome è obbligatorio (non può essere vuoto)
     *     - unique = true: non possono esserci due tipologie con lo stesso nome
     *     - length = 100: lunghezza massima di 100 caratteri
     *
     * ESEMPI:
     * - "Matrimonio"
     * - "Compleanno"
     * - "Evento aziendale"
     */
    @Column(nullable = false, unique = true, length = 100)
    private String nome;

    /**
     * DESCRIZIONE DELLA TIPOLOGIA (FACOLTATIVA)
     *
     * @Column(nullable = true):
     *     - nullable = true: la descrizione è facoltativa (può essere null)
     *     - length = 500: lunghezza massima di 500 caratteri
     *
     * ESEMPI:
     * - "Eventi di matrimonio con servizio catering completo"
     * - "Feste di compleanno per adulti e bambini"
     * - null (se non specificata)
     */
    @Column(nullable = true, length = 500)
    private String descrizione;

    // ========================================================================
    // COSTRUTTORI
    // ========================================================================

    /**
     * COSTRUTTORE SENZA PARAMETRI (RICHIESTO DA HIBERNATE)
     *
     * Hibernate ha bisogno di un costruttore vuoto per creare istanze della classe
     * quando legge i dati dal database.
     */
    public TipologiaEvento() {
    }

    /**
     * COSTRUTTORE CON NOME
     *
     * Crea una tipologia con solo il nome (descrizione opzionale)
     *
     * ESEMPIO DI USO:
     * TipologiaEvento t = new TipologiaEvento("Matrimonio");
     *
     * @param nome il nome della tipologia
     */
    public TipologiaEvento(String nome) {
        this.nome = nome;
    }

    /**
     * COSTRUTTORE CON NOME E DESCRIZIONE
     *
     * Crea una tipologia completa con nome e descrizione
     *
     * ESEMPIO DI USO:
     * TipologiaEvento t = new TipologiaEvento("Matrimonio", "Eventi di matrimonio con catering");
     *
     * @param nome il nome della tipologia
     * @param descrizione la descrizione della tipologia
     */
    public TipologiaEvento(String nome, String descrizione) {
        this.nome = nome;
        this.descrizione = descrizione;
    }

    // ========================================================================
    // GETTER E SETTER
    // ========================================================================

    /**
     * Restituisce l'ID della tipologia
     * @return l'ID univoco della tipologia
     */
    public Long getId() {
        return id;
    }

    /**
     * Imposta l'ID della tipologia (generalmente usato da Hibernate)
     * @param id l'ID da impostare
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Restituisce il nome della tipologia
     * @return il nome
     */
    public String getNome() {
        return nome;
    }

    /**
     * Imposta il nome della tipologia
     * @param nome il nuovo nome
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Restituisce la descrizione della tipologia
     * @return la descrizione (può essere null)
     */
    public String getDescrizione() {
        return descrizione;
    }

    /**
     * Imposta la descrizione della tipologia
     * @param descrizione la nuova descrizione
     */
    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    // ========================================================================
    // METODO toString (UTILE PER DEBUG)
    // ========================================================================

    /**
     * Ritorna una rappresentazione testuale della tipologia
     * Utile per debug e log
     *
     * ESEMPIO DI OUTPUT:
     * TipologiaEvento{id=1, nome='Matrimonio', descrizione='Eventi di matrimonio con catering'}
     */
    @Override
    public String toString() {
        return "TipologiaEvento{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", descrizione='" + descrizione + '\'' +
                '}';
    }
}
