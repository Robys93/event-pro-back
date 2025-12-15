package com.eventpro.catering.controller;

import com.eventpro.catering.dto.EventoRequestDTO;
import com.eventpro.catering.dto.EventoResponseDTO;
import com.eventpro.catering.service.EventoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}
