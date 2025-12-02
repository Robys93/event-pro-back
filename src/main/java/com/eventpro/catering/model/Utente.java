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
     * EMAIL DELL'UTENTE
     *
     * @Column(unique = true, nullable = false):
     *     - unique = true: non possono esserci due utenti con la stessa email
     *     - nullable = false: la email è obbligatoria (non può essere vuota)
     */
    @Column(unique = true, nullable = false)
    private String email;

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

    /**
     * RUOLO DELL'UTENTE
     *
     * @Column(nullable = false):
     *     - nullable = false: il ruolo è obbligatorio (non può essere vuoto)
     *     - rappresenta il ruolo assegnato all'utente, es. "USER", "ADMIN"
     *
     * IMPORTANTE:
     * - Può essere utilizzato da Spring Security per il controllo degli accessi
     * - Convenzione comune:
     *     - "ROLE_USER" per utenti standard
     *     - "ROLE_ADMIN" per amministratori
     */
    @Column(nullable = false)
    private String role;

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
     * Utente u = new Utente("test@example.com", "test", "USER");
     * utenteRepository.save(u);
     *
     * @param email l'email dell'utente
     * @param password la password dell'utente
     * @param role il ruolo dell'utente
     */
    public Utente(String email, String password, String role) {
        this.email = email;
        this.password = password;
        this.role = role;
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
     * Restituisce l'email dell'utente
     * @return l'email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Imposta l'email dell'utente
     * @param email la nuova email
     */
    public void setEmail(String email) {
        this.email = email;
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

    /**
     * Restituisce il ruolo dell'utente
     * @return il ruolo assegnato all'utente
     */
    public String getRole() {
        return role;
    }

    /**
     * Imposta il ruolo dell'utente
     * @param role il nuovo ruolo da assegnare
     */
    public void setRole(String role) {
        this.role = role;
    }


    // ========================================================================
    // METODO toString (FACOLTATIVO MA UTILE)
    // ========================================================================

    /**
     * Ritorna una rappresentazione testuale dell'utente
     * Serve per il debug (quando stampi l'oggetto nel log)
     *
     * ESEMPIO DI OUTPUT:
     * Utente{id=1, email='test@example.com', password='test', role='USER'}
     */
    @Override
    public String toString() {
        return "Utente{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                '}';
    }

}