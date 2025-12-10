package com.eventpro.catering.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * ENTITY EVENTO - RAPPRESENTA UN EVENTO NEL SISTEMA CATERING
 *
 * Un Entity è una classe Java che rappresenta una tabella nel database.
 * Questa entità gestisce gli eventi organizzati dal sistema catering.
 *
 * ESEMPI DI EVENTI:
 * - Matrimonio di Mario e Lucia il 15/06/2024 alle ore 18:00 presso Villa Roma
 * - Compleanno di Giovanni il 20/08/2024 alle 15:00 presso Ristorante Da Carlo
 * - Conferenza aziendale il 10/09/2024 dalle 9:00 alle 18:00 presso Hotel Marriott
 *
 * RELAZIONI:
 * - Ogni evento appartiene a una TIPOLOGIA (es: Matrimonio, Compleanno, Conferenza)
 * - Ogni evento può essere collegato a un CLIENTE (opzionale)
 *
 * COSA SUCCEDE:
 * 1. Spring crea automaticamente una tabella "evento" nel database
 * 2. Ogni istanza di Evento corrisponde a una riga della tabella
 * 3. Le relazioni con TipologiaEvento e Cliente vengono gestite tramite foreign key
 *
 * ANNOTAZIONI USATE:
 * - @Entity: dice a Hibernate che questa classe rappresenta una tabella
 * - @Table: specifica il nome della tabella nel database
 * - @Id: specifica la colonna che è la chiave primaria
 * - @GeneratedValue: genera automaticamente l'ID
 * - @Column: configura le proprietà delle colonne
 * - @ManyToOne: definisce una relazione molti-a-uno con un'altra entità
 * - @JoinColumn: specifica il nome della colonna foreign key
 */
@Entity
@Table(name = "evento")
public class Evento {

    /**
     * ID UNIVOCO DELL'EVENTO
     *
     * @Id: questa colonna è la chiave primaria (ogni evento ha un ID unico)
     * @GeneratedValue(strategy = GenerationType.IDENTITY): genera automaticamente l'ID
     * - IDENTITY: il database genera l'ID automaticamente (1, 2, 3, ...)
     *
     * ESEMPIO:
     * - Primo evento creato: ID = 1 (es. "Matrimonio Mario e Lucia")
     * - Secondo evento creato: ID = 2 (es. "Compleanno Giovanni")
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * NOME DELL'EVENTO
     *
     * @Column(nullable = false, length = 200):
     *     - nullable = false: il nome è obbligatorio (non può essere vuoto)
     *     - length = 200: lunghezza massima di 200 caratteri
     *
     * ESEMPI:
     * - "Matrimonio Mario Rossi e Lucia Bianchi"
     * - "Compleanno 50 anni Giovanni Verdi"
     * - "Conferenza Annuale Azienda XYZ"
     */
    @Column(nullable = false, length = 200)
    private String nome;

    /**
     * DATA DELL'EVENTO
     *
     * @Column(nullable = false):
     * - nullable = false: la data è obbligatoria
     *
     * TIPO: LocalDate (data senza orario, es: 2024-06-15)
     *
     * ESEMPI:
     * - LocalDate.of(2024, 6, 15) → 15 giugno 2024
     * - LocalDate.of(2024, 12, 31) → 31 dicembre 2024
     */
    @Column(nullable = false)
    private LocalDate data;

    /**
     * ORA DI INIZIO DELL'EVENTO
     *
     * @Column(nullable = false):
     * - nullable = false: l'ora di inizio è obbligatoria
     *
     * TIPO: LocalTime (orario senza data, es: 18:00)
     *
     * ESEMPI:
     * - LocalTime.of(18, 0) → 18:00
     * - LocalTime.of(14, 30) → 14:30
     * - LocalTime.of(9, 0) → 09:00
     */
    @Column(name = "ora_inizio", nullable = false)
    private LocalTime oraInizio;

    /**
     * ORA DI FINE DELL'EVENTO
     *
     * @Column(nullable = false):
     * - nullable = false: l'ora di fine è obbligatoria
     *
     * TIPO: LocalTime (orario senza data, es: 23:00)
     *
     * ESEMPI:
     * - LocalTime.of(23, 0) → 23:00
     * - LocalTime.of(17, 30) → 17:30
     * - LocalTime.of(18, 0) → 18:00
     */
    @Column(name = "ora_fine", nullable = false)
    private LocalTime oraFine;

    /**
     * LOCATION (LUOGO) DELL'EVENTO
     *
     * @Column(nullable = false, length = 300):
     * - nullable = false: la location è obbligatoria
     * - length = 300: lunghezza massima di 300 caratteri
     *
     * ESEMPI:
     * - "Villa Roma, Via dei Fiori 123, Roma"
     * - "Ristorante Da Carlo, Piazza Garibaldi 45, Milano"
     * - "Hotel Marriott, Sala Conferenze A, Via Veneto 88, Roma"
     */
    @Column(nullable = false, length = 300)
    private String location;

    /**
     * NOTE SULL'EVENTO (FACOLTATIVE)
     *
     * @Column(nullable = true, columnDefinition = "TEXT"):
     * - nullable = true: le note sono facoltative (possono essere null)
     * - columnDefinition = "TEXT": campo di testo lungo (fino a 65. 535 caratteri)
     *
     * ESEMPI:
     * - "Menu vegetariano per 5 ospiti. Allestimento con fiori bianchi."
     * - "Richiesta proiettore e microfono per presentazione."
     * - null (se non ci sono note)
     */
    @Column(nullable = true, columnDefinition = "TEXT")
    private String note;

    /**
     * FLAG EVENTO GIORNALIERO
     *
     * @Column(nullable = false):
     * - nullable = false: il flag è obbligatorio (deve essere true o false)
     *
     * COSA SIGNIFICA:
     * - true: l'evento dura tutto il giorno (es: conferenza dalle 9:00 alle 18:00)
     * - false: l'evento ha orari specifici (es: cena dalle 20:00 alle 23:00)
     *
     * ESEMPI:
     * - Conferenza aziendale → eventoGiornaliero = true
     * - Cena di matrimonio → eventoGiornaliero = false
     */
    @Column(name = "evento_giornaliero", nullable = false)
    private Boolean eventoGiornaliero;

    /**
     * RELAZIONE CON TIPOLOGIA_EVENTO (OBBLIGATORIA)
     *
     * @ManyToOne:  Relazione molti-a-uno (molti eventi appartengono a una tipologia)
     * @JoinColumn:  Specifica il nome della colonna foreign key nel database
     * - name = "tipologia_evento_id": nome della colonna FK
     * - nullable = false: la tipologia è OBBLIGATORIA (ogni evento deve avere una tipologia)
     *
     * ESEMPIO:
     * - Evento "Matrimonio Mario e Lucia" → tipologia = TipologiaEvento{id=1, nome="Matrimonio"}
     * - Evento "Compleanno Giovanni" → tipologia = TipologiaEvento{id=2, nome="Compleanno"}
     *
     * QUERY SQL GENERATA:
     * - FOREIGN KEY (tipologia_evento_id) REFERENCES tipologia_evento(id)
     */
    @ManyToOne
    @JoinColumn(name = "tipologia_evento_id", nullable = false)
    private TipologiaEvento tipologiaEvento;

    /**
     * RELAZIONE CON CLIENTE (OPZIONALE)
     *
     * @ManyToOne:  Relazione molti-a-uno (molti eventi possono appartenere a un cliente)
     * @JoinColumn: Specifica il nome della colonna foreign key nel database
     * - name = "cliente_id": nome della colonna FK
     * - nullable = true: il cliente è OPZIONALE (un evento può non avere un cliente)
     *
     * ESEMPIO:
     * - Evento "Matrimonio Mario e Lucia" → cliente = Cliente{id=5, nome="Mario", cognome="Rossi"}
     * - Evento "Conferenza aziendale" → cliente = null (nessun cliente specifico)
     *
     * QUERY SQL GENERATA:
     * - FOREIGN KEY (cliente_id) REFERENCES cliente(id)
     *
     * NOTA: Questa relazione sarà popolata quando il Task BE-03 (Cliente) sarà completato
     */
    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = true)
    private Cliente cliente;

    // ========================================================================
    // COSTRUTTORI
    // ========================================================================

    /**
     * COSTRUTTORE VUOTO (RICHIESTO DA HIBERNATE)
     *
     * Hibernate ha bisogno di un costruttore vuoto per creare istanze della classe
     * quando legge i dati dal database.
     */
    public Evento() {
    }

    /**
     * COSTRUTTORE COMPLETO (SENZA CLIENTE)
     *
     * Crea un evento completo senza cliente collegato.
     * Utile per eventi che non hanno un cliente specifico.
     *
     * @param nome Nome dell'evento
     * @param data Data dell'evento
     * @param oraInizio Ora di inizio
     * @param oraFine Ora di fine
     * @param location Luogo dell'evento
     * @param note Note sull'evento (può essere null)
     * @param eventoGiornaliero Flag evento giornaliero
     * @param tipologiaEvento Tipologia dell'evento
     */
    public Evento(String nome, LocalDate data, LocalTime oraInizio, LocalTime oraFine,
                  String location, String note, Boolean eventoGiornaliero,
                  TipologiaEvento tipologiaEvento) {
        this.nome = nome;
        this.data = data;
        this.oraInizio = oraInizio;
        this.oraFine = oraFine;
        this.location = location;
        this.note = note;
        this.eventoGiornaliero = eventoGiornaliero;
        this.tipologiaEvento = tipologiaEvento;
        this.cliente = null;  // Nessun cliente
    }

    /**
     * COSTRUTTORE COMPLETO (CON CLIENTE)
     *
     * Crea un evento completo con cliente collegato.
     *
     * @param nome Nome dell'evento
     * @param data Data dell'evento
     * @param oraInizio Ora di inizio
     * @param oraFine Ora di fine
     * @param location Luogo dell'evento
     * @param note Note sull'evento (può essere null)
     * @param eventoGiornaliero Flag evento giornaliero
     * @param tipologiaEvento Tipologia dell'evento
     * @param cliente Cliente collegato (può essere null)
     */
    public Evento(String nome, LocalDate data, LocalTime oraInizio, LocalTime oraFine,
                  String location, String note, Boolean eventoGiornaliero,
                  TipologiaEvento tipologiaEvento, Cliente cliente) {
        this.nome = nome;
        this.data = data;
        this.oraInizio = oraInizio;
        this. oraFine = oraFine;
        this.location = location;
        this.note = note;
        this.eventoGiornaliero = eventoGiornaliero;
        this.tipologiaEvento = tipologiaEvento;
        this.cliente = cliente;
    }

    // ========================================================================
    // GETTER E SETTER
    // ========================================================================

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public LocalTime getOraInizio() {
        return oraInizio;
    }

    public void setOraInizio(LocalTime oraInizio) {
        this.oraInizio = oraInizio;
    }

    public LocalTime getOraFine() {
        return oraFine;
    }

    public void setOraFine(LocalTime oraFine) {
        this.oraFine = oraFine;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Boolean getEventoGiornaliero() {
        return eventoGiornaliero;
    }

    public void setEventoGiornaliero(Boolean eventoGiornaliero) {
        this.eventoGiornaliero = eventoGiornaliero;
    }

    public TipologiaEvento getTipologiaEvento() {
        return tipologiaEvento;
    }

    public void setTipologiaEvento(TipologiaEvento tipologiaEvento) {
        this.tipologiaEvento = tipologiaEvento;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    // ========================================================================
    // METODO toString (UTILE PER DEBUG)
    // ========================================================================

    /**
     * Ritorna una rappresentazione testuale dell'evento
     * Utile per debug e log
     *
     * ESEMPIO DI OUTPUT:
     * Evento{id=1, nome='Matrimonio Mario e Lucia', data=2024-06-15,
     *        oraInizio=18:00, oraFine=23:00, location='Villa Roma',
     *        eventoGiornaliero=false, tipologia='Matrimonio'}
     */
    @Override
    public String toString() {
        return "Evento{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", data=" + data +
                ", oraInizio=" + oraInizio +
                ", oraFine=" + oraFine +
                ", location='" + location + '\'' +
                ", note='" + note + '\'' +
                ", eventoGiornaliero=" + eventoGiornaliero +
                ", tipologiaEvento=" + (tipologiaEvento != null ? tipologiaEvento.getNome() : "null") +
                ", cliente=" + (cliente != null ? cliente.getNome() + " " + cliente.getCognome() : "null") +
                '}';
    }
}