package com.eventpro.catering.dto;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * DTO PER LA RISPOSTA EVENTO
 *
 * Versione SEMPLIFICATA - solo gestione evento, senza cliente
 */
public class EventoResponseDTO {

    private Long id;
    private String nome;
    private LocalDate data;
    private LocalTime oraInizio;
    private LocalTime oraFine;
    private String location;
    private String note;
    private Boolean eventoGiornaliero;
    private TipologiaEventoDTO tipologiaEvento;

    /**
     * DTO NESTED PER TIPOLOGIA EVENTO
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

    // Costruttori
    public EventoResponseDTO() {
    }

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
    }

    // Getter e Setter
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
}
