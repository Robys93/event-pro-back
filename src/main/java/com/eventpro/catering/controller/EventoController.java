package com.eventpro.catering.controller;

import com.eventpro.catering.dto.EventoRequestDTO;
import com.eventpro.catering.dto.EventoResponseDTO;
import com.eventpro.catering.service.EventoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * CONTROLLER REST PER LA GESTIONE DEGLI EVENTI
 *
 * Espone gli endpoint:
 * - POST /api/eventi      -> crea un nuovo evento
 * - GET  /api/eventi/{id} -> restituisce il dettaglio di un evento
 *
 * Nessuna logica di utenti, solo gestione eventi.
 */
@RestController
@RequestMapping("/api/eventi")
public class EventoController {

    private final EventoService eventoService;

    public EventoController(EventoService eventoService) {
        this.eventoService = eventoService;
    }

    /**
     * CREA UN NUOVO EVENTO
     *
     * POST /api/eventi
     *
     * @param request DTO con i dati dell'evento
     * @return EventoResponseDTO con HTTP 201
     */
    @PostMapping
    public ResponseEntity<EventoResponseDTO> createEvento(@RequestBody EventoRequestDTO request) {
        EventoResponseDTO response = eventoService.createEvento(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * RESTITUISCE IL DETTAGLIO DI UN EVENTO PER ID
     *
     * GET /api/eventi/{id}
     *
     * @param id ID dell'evento
     * @return EventoResponseDTO con HTTP 200 (o 404 gestito dal GlobalExceptionHandler)
     */
    @GetMapping("/{id}")
    public ResponseEntity<EventoResponseDTO> getEventoById(@PathVariable Long id) {
        EventoResponseDTO response = eventoService.getEventoById(id);
        return ResponseEntity.ok(response);
    }

    /**
     * RESTITUISCE LISTA EVENTI (con filtro opzionale per data)
     *
     * GET /api/eventi
     * GET /api/eventi?data=2024-06-15
     *
     * Questo endpoint supporta due modalità:
     * 1. SENZA PARAMETRO: restituisce tutti gli eventi
     * 2. CON PARAMETRO data: restituisce solo eventi di quella data
     *
     * @RequestParam(required = false):
     * - Il parametro è opzionale
     * - Se non fornito, data sarà null
     *
     * @DateTimeFormat(iso = DateTimeFormat.ISO.DATE):
     * - Converte automaticamente "2024-06-15" in LocalDate
     * - Formato ISO 8601 standard
     *
     * ESEMPI DI RICHIESTA:
     *
     * 1. Tutti gli eventi:
     *    GET http://localhost:8080/api/eventi
     *    Risposta: [ {evento1}, {evento2}, ... ]
     *
     * 2. Eventi filtrati:
     *    GET http://localhost:8080/api/eventi? data=2024-06-15
     *    Risposta: [ {evento1}, {evento2} ] (solo del 15 giugno)
     *
     * POSSIBILI ERRORI:
     * - 400 BAD REQUEST: formato data non valido (es: "2024-13-45")
     * - 200 OK con lista vuota []:  nessun evento trovato
     *
     * @param data Data opzionale per filtrare (può essere null)
     * @return ResponseEntity con lista di eventi e HTTP 200
     */
    @GetMapping
    public ResponseEntity<List<EventoResponseDTO>> getEventi(
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate data) {

        // Dichiara la lista che conterrà i risultati
        List<EventoResponseDTO> eventi;

        // LOGICA CONDIZIONALE: Verifica se il parametro data è presente
        if (data != null) {
            // CASO 1: Parametro data fornito
            // Esempio: GET /api/eventi?data=2024-06-15
            // → Chiama il service per recuperare eventi filtrati
            eventi = eventoService.getEventiByData(data);
        } else {
            // CASO 2: Nessun parametro fornito
            // Esempio: GET /api/eventi
            // → Chiama il service per recuperare tutti gli eventi
            eventi = eventoService.getAllEventi();
        }

        // Restituisce la risposta HTTP 200 OK con la lista di eventi
        return ResponseEntity.ok(eventi);
    }
}
