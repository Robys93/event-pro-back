package com.eventpro.catering.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * GLOBAL EXCEPTION HANDLER
 *
 * Questa classe gestisce centralmente tutte le exception lanciate
 * dai controller REST dell'applicazione.
 *
 * @RestControllerAdvice:
 * - Dice a Spring di applicare questo handler a tutti i @RestController
 * - Intercetta le exception prima che vengano restituite al client
 * - Permette di personalizzare le risposte di errore
 *
 * VANTAGGI:
 * - Gestione centralizzata degli errori
 * - Risposte JSON uniformi
 * - Codici HTTP appropriati
 * - Messaggi di errore user-friendly
 *
 * COME FUNZIONA:
 * 1. Un controller lancia un'exception (es: EventoNotFoundException)
 * 2. Spring intercetta l'exception
 * 3. Cerca un metodo @ExceptionHandler che gestisca quel tipo di exception
 * 4. Esegue il metodo e restituisce la risposta al client
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * GESTISCE EventoNotFoundException
     *
     * Quando viene lanciata EventoNotFoundException, questo metodo
     * intercetta l'errore e restituisce una risposta HTTP 404.
     *
     * @param ex L'exception lanciata
     * @return ResponseEntity con HTTP 404 e messaggio di errore
     *
     * ESEMPIO DI RISPOSTA:
     * Status: 404 NOT FOUND
     * Body:
     * {
     *   "timestamp": "2024-12-15T10:05:30",
     *   "status": 404,
     *   "error": "Not Found",
     *   "message": "Evento non trovato con ID: 999"
     * }
     */
    @ExceptionHandler(EventoNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleEventoNotFound(EventoNotFoundException ex) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("timestamp", LocalDateTime.now());
        errorResponse.put("status", HttpStatus.NOT_FOUND.value());
        errorResponse.put("error", "Not Found");
        errorResponse.put("message", ex.getMessage());

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    /**
     * GESTISCE ERRORI DI VALIDAZIONE
     *
     * Quando i dati in input non passano le validazioni (@Valid nel controller),
     * Spring lancia MethodArgumentNotValidException.
     * Questo metodo intercetta l'errore e restituisce HTTP 400 con i dettagli
     * di quali campi hanno fallito la validazione.
     *
     * @param ex L'exception con i dettagli degli errori di validazione
     * @return ResponseEntity con HTTP 400 e mappa degli errori per campo
     *
     * ESEMPIO DI RISPOSTA:
     * Status: 400 BAD REQUEST
     * Body:
     * {
     *   "timestamp": "2024-12-15T10:05:30",
     *   "status": 400,
     *   "error": "Validation Failed",
     *   "errors": {
     *     "nome": "Il nome dell'evento è obbligatorio",
     *     "data": "La data dell'evento non può essere nel passato",
     *     "oraInizio": "L'ora di inizio è obbligatoria"
     *   }
     * }
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationErrors(MethodArgumentNotValidException ex) {
        Map<String, String> fieldErrors = new HashMap<>();

        // Raccoglie tutti gli errori di validazione per campo
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            fieldErrors.put(fieldName, errorMessage);
        });

        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("timestamp", LocalDateTime.now());
        errorResponse.put("status", HttpStatus.BAD_REQUEST.value());
        errorResponse.put("error", "Validation Failed");
        errorResponse.put("errors", fieldErrors);

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    /**
     * GESTISCE IllegalArgumentException
     *
     * Questa exception viene lanciata per errori logici, ad esempio:
     * - OraFine prima di OraInizio
     * - TipologiaEvento non esistente
     * - Cliente non esistente
     *
     * @param ex L'exception lanciata
     * @return ResponseEntity con HTTP 400 e messaggio di errore
     *
     * ESEMPIO DI RISPOSTA:
     * Status: 400 BAD REQUEST
     * Body:
     * {
     *   "timestamp": "2024-12-15T10:05:30",
     *   "status": 400,
     *   "error": "Bad Request",
     *   "message": "L'ora di fine deve essere successiva all'ora di inizio"
     * }
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> handleIllegalArgument(IllegalArgumentException ex) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("timestamp", LocalDateTime.now());
        errorResponse.put("status", HttpStatus.BAD_REQUEST.value());
        errorResponse.put("error", "Bad Request");
        errorResponse.put("message", ex.getMessage());

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    /**
     * GESTISCE TUTTE LE ALTRE EXCEPTION NON GESTITE
     *
     * Questo è un catch-all per qualsiasi exception non prevista.
     * Restituisce HTTP 500 (Internal Server Error).
     *
     * @param ex L'exception generica
     * @return ResponseEntity con HTTP 500 e messaggio generico
     *
     * ESEMPIO DI RISPOSTA:
     * Status: 500 INTERNAL SERVER ERROR
     * Body:
     * {
     *   "timestamp": "2024-12-15T10:05:30",
     *   "status": 500,
     *   "error": "Internal Server Error",
     *   "message": "Si è verificato un errore imprevisto"
     * }
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGenericException(Exception ex) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("timestamp", LocalDateTime.now());
        errorResponse.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        errorResponse.put("error", "Internal Server Error");
        errorResponse.put("message", "Si è verificato un errore imprevisto");

        // Log dell'errore per debugging (importante!)
        System.err.println("ERROR: " + ex.getMessage());
        ex.printStackTrace();

        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
