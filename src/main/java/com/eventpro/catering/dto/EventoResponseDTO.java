package com.eventpro.catering.dto;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * DTO PER LA RISPOSTA EVENTO
 *
 * Questo DTO (Data Transfer Object) viene usato per inviare i dati
 * dell'evento nelle risposte delle API GET e POST.
 *
 * PERCHÉ USARE UN DTO INVECE DELL'ENTITY:
 * 1. Controllo: decidiamo esattamente quali dati esporre al client
 * 2. Sicurezza: non esponiamo informazioni sensibili
 * 3. Flessibilità: possiamo formattare i dati come vogliamo
 * 4. Performance: evitiamo lazy loading problems con le relazioni
 *
 * ESEMPIO DI JSON IN USCITA:
 * {
 *   "id": 1,
 *   "nome": "Matrimonio Mario e Lucia",
 *   "data": "2024-06-15",
 *   "oraInizio": "18:00",
 *   "oraFine": "23:00",
 *   "location": "Villa Roma, Via dei Fiori 123",
 *   "note": "Menu vegetariano per 5 ospiti",
 *   "eventoGiornaliero": false,
 *   "tipologiaEvento": {
 *     "id": 1,
 *     "nome": "Matrimonio"
 *   },
 *   "cliente": {
 *     "id": 5,
 *     "nome": "Mario",
 *     "cognome": "Rossi"
 *   }
 * }
 *
 * DIFFERENZE CON EventoRequestDTO:
 * - Ha l'ID (generato dal database)
 * - Contiene oggetti nested per tipologia e cliente (non solo ID)
 * - Non ha validazioni (i dati vengono dal database, sono già validi)
 */
public class EventoResponseDTO {

    /**
     * ID DELL'EVENTO (GENERATO DAL DATABASE)
     */
    private Long id;

    /**
     * NOME DELL'EVENTO
     */
    private String nome;

    /**
     * DATA DELL'EVENTO
     *
     * FORMATO JSON: "2024-06-15"
     */
    private LocalDate data;

    /**
     * ORA DI INIZIO
     *
     * FORMATO JSON: "18:00"
     */
    private LocalTime oraInizio;

    /**
     * ORA DI FINE
     *
     * FORMATO JSON: "23:00"
     */
    private LocalTime oraFine;

    /**
     * LOCATION (LUOGO)
     */
    private String location;

    /**
     * NOTE (FACOLTATIVE)
     *
     * Può essere null nel JSON
     */
    private String note;

    /**
     * FLAG EVENTO GIORNALIERO
     */
    private Boolean eventoGiornaliero;

    /**
     * TIPOLOGIA EVENTO (NESTED OBJECT)
     *
     * Invece di esporre solo l'ID, esponiamo un oggetto con id e nome.
     * Questo è più comodo per il frontend.
     */
    private TipologiaEventoDTO tipologiaEvento;

    /**
     * CLIENTE (NESTED OBJECT - FACOLTATIVO)
     *
     * Può essere null se l'evento non ha un cliente
     */
    private ClienteDTO cliente;

    // ========================================================================
    // INNER CLASSES PER NESTED OBJECTS
    // ========================================================================

    /**
     * DTO NESTED PER TIPOLOGIA EVENTO
     *
     * Contiene solo i dati essenziali della tipologia
     */
    public static class TipologiaEventoDTO {
        private Long id;
        private String nome;

        public TipologiaEventoDTO() {
        }

        public TipologiaEventoDTO(Long id, String nome) {
            this.id = id;
            this.nome = nome;
        }

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
    }

    /**
     * DTO NESTED PER CLIENTE
     *
     * Contiene solo i dati essenziali del cliente
     */
    public static class ClienteDTO {
        private Long id;
        private String nome;
        private String cognome;

        public ClienteDTO() {
        }

        public ClienteDTO(Long id, String nome, String cognome) {
            this.id = id;
            this.nome = nome;
            this.cognome = cognome;
        }

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

        public String getCognome() {
            return cognome;
        }

        public void setCognome(String cognome) {
            this.cognome = cognome;
        }
    }

    // ========================================================================
    // COSTRUTTORI
    // ========================================================================

    /**
     * Costruttore vuoto
     */
    public EventoResponseDTO() {
    }

    /**
     * Costruttore completo (senza cliente)
     */
    public EventoResponseDTO(Long id, String nome, LocalDate data, LocalTime oraInizio,
                             LocalTime oraFine, String location, String note,
                             Boolean eventoGiornaliero, TipologiaEventoDTO tipologiaEvento) {
        this.id = id;
        this.nome = nome;
        this.data = data;
        this.oraInizio = oraInizio;
        this.oraFine = oraFine;
        this.location = location;
        this.note = note;
        this.eventoGiornaliero = eventoGiornaliero;
        this.tipologiaEvento = tipologiaEvento;
        this.cliente = null;
    }

    /**
     * Costruttore completo (con cliente)
     */
    public EventoResponseDTO(Long id, String nome, LocalDate data, LocalTime oraInizio,
                             LocalTime oraFine, String location, String note,
                             Boolean eventoGiornaliero, TipologiaEventoDTO tipologiaEvento,
                             ClienteDTO cliente) {
        this.id = id;
        this.nome = nome;
        this.data = data;
        this.oraInizio = oraInizio;
        this.oraFine = oraFine;
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

    public TipologiaEventoDTO getTipologiaEvento() {
        return tipologiaEvento;
    }

    public void setTipologiaEvento(TipologiaEventoDTO tipologiaEvento) {
        this.tipologiaEvento = tipologiaEvento;
    }

    public ClienteDTO getCliente() {
        return cliente;
    }

    public void setCliente(ClienteDTO cliente) {
        this.cliente = cliente;
    }
}
