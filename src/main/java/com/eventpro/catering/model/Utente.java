package com.eventpro.catering.model;

import jakarta.persistence.*;

/**
 * ENTITY UTENTE - RAPPRESENTA UN UTENTE NEL DATABASE
 *
 * Un Entity è una classe Java che rappresenta una tabella nel database.
 * Ogni proprietà della classe corrisponde a una colonna della tabella.
 *
 * COSA SUCCEDE:
 * 1. Spring crea automaticamente una tabella "UTENTE" nel database
 * 2. Ogni istanza di Utente corrisponde a una riga della tabella
 * 3. Quando salvi un Utente nel database, Hibernate genera la query SQL per te
 *
 * ANNOTAZIONI USATE:
 * - @Entity: dice a Hibernate che questa classe rappresenta una tabella
 * - @Table: specifica il nome della tabella nel database (opzionale, usa il nome della classe se non specificato)
 * - @Id: specifica la colonna che è la chiave primaria (identificatore unico di ogni riga)
 * - @GeneratedValue: genera automaticamente l'ID (1, 2, 3, ...)
 * - @Column: configura le proprietà della colonna nel database
 */
@Entity
@Table(name = "UTENTE")
public class Utente {

    /**
     * ID UNIVOCO DELL'UTENTE
     *
     * @Id: questa colonna è la chiave primaria (ogni utente ha un ID unico)
     * @GeneratedValue(strategy = GenerationType.IDENTITY): genera automaticamente l'ID
     *     - IDENTITY: il database genera l'ID automaticamente (1, 2, 3, ...)
     *     - Ogni nuovo utente riceve un ID univoco
     *
     * ESEMPIO:
     * - Primo utente creato: ID = 1
     * - Secondo utente creato: ID = 2
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * NOME_UTENTE DELL'UTENTE
     *
     * @Column(unique = true, nullable = false):
     *     - unique = true: non possono esserci due utenti con lo stesso nome_utente
     *     - nullable = false: il nome_utente è obbligatorio (non può essere vuoto)
     */
    @Column(unique = true, nullable = false)
    private String nome_utente;

    /**
     * PASSWORD DELL'UTENTE
     *
     * @Column(nullable = false):
     *     - nullable = false: la password è obbligatoria (non può essere vuota)
     *
     * NOTA DI SICUREZZA:
     * - In un'applicazione reale, la password deve essere HASH-ata (crittografata)
     * - Qui la salviamo in chiaro solo perché è un progetto di testing con H2
     * - IMPORTANTE: NON fare così in produzione!  Usa bcrypt o altre librerie di hashing
     */
    @Column(nullable = false)
    private String password;

    // ========================================================================
    // COSTRUTTORI
    // ========================================================================

    /**
     * COSTRUTTORE SENZA PARAMETRI (RICHIESTO DA HIBERNATE)
     *
     * Hibernate ha bisogno di un costruttore vuoto per creare istanze della classe
     * quando legge i dati dal database.
     * È una convenzione del framework JPA.
     */
    public Utente() {
    }

    /**
     * COSTRUTTORE CON PARAMETRI
     *
     * Serve per creare facilmente un nuovo Utente dal codice
     *
     * ESEMPIO DI USO:
     * Utente u = new Utente("test", "test");
     * utenteRepository.save(u);
     *
     * @param nome_utente il nome utente
     * @param password la password dell'utente
     */
    public Utente(String nome_utente, String password) {
        this.nome_utente = nome_utente;
        this.password = password;
    }

    // ========================================================================
    // GETTER E SETTER
    // ========================================================================
    // I getter e setter permettono di leggere e modificare le proprietà dell'utente

    /**
     * Restituisce l'ID dell'utente
     * @return l'ID univoco dell'utente
     */
    public Long getId() {
        return id;
    }

    /**
     * Imposta l'ID dell'utente (generalmente usato da Hibernate)
     * @param id l'ID da impostare
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Restituisce il nome_utente dell'utente
     * @return il nome_utente
     */
    public String getNome_utente() {
        return nome_utente;
    }

    /**
     * Imposta lo nome_utente dell'utente
     * @param nome_utente il nuovo nome_utente
     */
    public void setNome_utente(String nome_utente) {
        this.nome_utente = nome_utente;
    }

    /**
     * Restituisce la password dell'utente
     * @return la password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Imposta la password dell'utente
     * @param password la nuova password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    // ========================================================================
    // METODO toString (FACOLTATIVO MA UTILE)
    // ========================================================================

    /**
     * Ritorna una rappresentazione testuale dell'utente
     * Serve per il debug (quando stampi l'oggetto nel log)
     *
     * ESEMPIO DI OUTPUT:
     * Utente{id=1, nome_utente='test', password='test'}
     */
    @Override
    public String toString() {
        return "Utente{" +
                "id=" + id +
                ", nome_utente='" + nome_utente + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

}