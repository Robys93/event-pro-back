package com.eventpro.catering.dto;

import jakarta.validation.constraints.*;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * DTO PER LA RICHIESTA DI CREAZIONE EVENTO
 *
 * Questo DTO (Data Transfer Object) viene usato per ricevere i dati
 * dalla richiesta POST /eventi quando si vuole creare un nuovo evento.
 *
 * PERCHÉ USARE UN DTO INVECE DELL'ENTITY:
 * 1. Sicurezza: non esponiamo direttamente l'entity al client
 * 2. Validazione: possiamo validare i dati in ingresso con @Valid
 * 3. Flessibilità: il DTO può avere campi diversi dall'entity
 * 4. Disaccoppiamento: possiamo cambiare l'entity senza modificare l'API
 *
 * ESEMPIO DI JSON IN INGRESSO:
 * {
 *   "nome": "Matrimonio Mario e Lucia",
 *   "data": "2024-06-15",
 *   "oraInizio": "18:00",
 *   "oraFine": "23:00",
 *   "location": "Villa Roma, Via dei Fiori 123",
 *   "note": "Menu vegetariano per 5 ospiti",
 *   "eventoGiornaliero": false,
 *   "tipologiaEventoId": 1,
 *   "clienteId": 5
 * }
 *
 * VALIDAZIONI:
 * - @NotBlank: il campo non può essere vuoto
 * - @NotNull: il campo non può essere null
 * - @Size: limita la lunghezza del campo
 * - @FutureOrPresent: la data deve essere oggi o futura
 * - @Positive: il valore deve essere positivo
 */
public class EventoRequestDTO {

    /**
     * NOME DELL'EVENTO
     *
     * @NotBlank: non può essere vuoto o contenere solo spazi
     * @Size: lunghezza massima 200 caratteri
     */
    @NotBlank(message = "Il nome dell'evento è obbligatorio")
    @Size(max = 200, message = "Il nome non può superare 200 caratteri")
    private String nome;

    /**
     * DATA DELL'EVENTO
     *
     * @NotNull: non può essere null
     * @FutureOrPresent: deve essere oggi o nel futuro (non nel passato)
     *
     * FORMATO: "2024-06-15" (ISO 8601: yyyy-MM-dd)
     */
    @NotNull(message = "La data dell'evento è obbligatoria")
    @FutureOrPresent(message = "La data dell'evento non può essere nel passato")
    private LocalDate data;

    /**
     * ORA DI INIZIO
     *
     * @NotNull: non può essere null
     *
     * FORMATO: "18:00" (HH:mm)
     */
    @NotNull(message = "L'ora di inizio è obbligatoria")
    private LocalTime oraInizio;

    /**
     * ORA DI FINE
     *
     * @NotNull: non può essere null
     *
     * FORMATO: "23:00" (HH:mm)
     *
     * NOTA: La validazione che oraFine > oraInizio sarà fatta nel Service
     */
    @NotNull(message = "L'ora di fine è obbligatoria")
    private LocalTime oraFine;

    /**
     * LOCATION (LUOGO)
     *
     * @NotBlank: non può essere vuoto
     * @Size: lunghezza massima 300 caratteri
     */
    @NotBlank(message = "La location è obbligatoria")
    @Size(max = 300, message = "La location non può superare 300 caratteri")
    private String location;

    /**
     * NOTE (FACOLTATIVE)
     *
     * Nessuna validazione: può essere null o vuoto
     */
    private String note;

    /**
     * FLAG EVENTO GIORNALIERO
     *
     * @NotNull: non può essere null (deve essere true o false)
     */
    @NotNull(message = "Il flag evento giornaliero è obbligatorio")
    private Boolean eventoGiornaliero;

    /**
     * ID DELLA TIPOLOGIA EVENTO
     *
     * @NotNull: non può essere null
     * @Positive: deve essere un numero positivo (>0)
     *
     * ESEMPIO: 1 (ID della tipologia "Matrimonio")
     */
    @NotNull(message = "L'ID della tipologia evento è obbligatorio")
    @Positive(message = "L'ID della tipologia deve essere un numero positivo")
    private Long tipologiaEventoId;

    /**
     * ID DEL CLIENTE (FACOLTATIVO)
     *
     * @Positive: se fornito, deve essere positivo
     *
     * Può essere null se l'evento non ha un cliente specifico
     */
    @Positive(message = "L'ID del cliente deve essere un numero positivo")
    private Long clienteId;

    // ========================================================================
    // COSTRUTTORI
    // ========================================================================

    /**
     * Costruttore vuoto (richiesto per la deserializzazione JSON)
     */
    public EventoRequestDTO() {
    }

    /**
     * Costruttore completo per test e creazione manuale
     */
    public EventoRequestDTO(String nome, LocalDate data, LocalTime oraInizio, LocalTime oraFine,
                            String location, String note, Boolean eventoGiornaliero,
                            Long tipologiaEventoId, Long clienteId) {
        this.nome = nome;
        this.data = data;
        this.oraInizio = oraInizio;
        this.oraFine = oraFine;
        this.location = location;
        this.note = note;
        this.eventoGiornaliero = eventoGiornaliero;
        this.tipologiaEventoId = tipologiaEventoId;
        this.clienteId = clienteId;
    }

    // ========================================================================
    // GETTER E SETTER
    // ========================================================================

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

    public Long getTipologiaEventoId() {
        return tipologiaEventoId;
    }

    public void setTipologiaEventoId(Long tipologiaEventoId) {
        this.tipologiaEventoId = tipologiaEventoId;
    }

    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }
}
