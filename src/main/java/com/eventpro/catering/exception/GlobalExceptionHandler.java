package com.eventpro.catering.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * GESTORE GLOBALE DELLE ECCEZIONI PER LE API
 *
 * Qui gestiamo in modo centralizzato gli errori legati agli eventi,
 * restituendo HTTP status e messaggi chiari al frontend.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EventoNotFoundException.class)
    public ResponseEntity<String> handleEventoNotFound(EventoNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgument(IllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
}
