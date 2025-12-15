package com.eventpro.catering.dto;

import jakarta.validation.constraints.*;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * DTO DI RICHIESTA PER LA CREAZIONE DI UN EVENTO
 *
 * Versione SEMPLIFICATA - solo gestione evento, senza cliente
 */
public class EventoRequestDTO {

    @NotBlank(message = "Il nome dell'evento è obbligatorio")
    private String nome;

    @NotNull(message = "La data dell'evento è obbligatoria")
    @FutureOrPresent(message = "La data dell'evento non può essere nel passato")
    private LocalDate data;

    @NotNull(message = "L'ora di inizio è obbligatoria")
    private LocalTime oraInizio;

    @NotNull(message = "L'ora di fine è obbligatoria")
    private LocalTime oraFine;

    @NotBlank(message = "La location è obbligatoria")
    private String location;

    private String note;

    private Boolean eventoGiornaliero = false;

    @NotNull(message = "La tipologia evento è obbligatoria")
    @Positive(message = "L'ID della tipologia evento deve essere positivo")
    private Long tipologiaEventoId;

    // Costruttori
    public EventoRequestDTO() {
    }

    public EventoRequestDTO(String nome, LocalDate data, LocalTime oraInizio,
                            LocalTime oraFine, String location, String note,
                            Boolean eventoGiornaliero, Long tipologiaEventoId) {
        this.nome = nome;
        this.data = data;
        this.oraInizio = oraInizio;
        this.oraFine = oraFine;
        this.location = location;
        this.note = note;
        this.eventoGiornaliero = eventoGiornaliero;
        this.tipologiaEventoId = tipologiaEventoId;
    }

    // Getter e Setter
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
}
