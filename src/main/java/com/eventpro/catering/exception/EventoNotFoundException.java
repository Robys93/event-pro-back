package com.eventpro.catering.exception;

/**
 * ECCEZIONE PERSONALIZZATA PER EVENTO NON TROVATO
 *
 * Viene lanciata quando si cerca un evento per ID
 * e non esiste nel database.
 */
public class EventoNotFoundException extends RuntimeException {

    public EventoNotFoundException(Long id) {
        super("Evento non trovato con id: " + id);
    }
}
