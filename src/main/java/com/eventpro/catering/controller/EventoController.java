package com.eventpro.catering.controller;

import com.eventpro.catering.dto.EventoRequestDTO;
import com.eventpro.catering.dto.EventoResponseDTO;
import com.eventpro.catering.service.EventoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * CONTROLLER REST PER LA GESTIONE DEGLI EVENTI
 *
 * Questo controller espone gli endpoint API per:
 * - Creare nuovi eventi (POST /eventi)
 * - Recuperare un evento per ID (GET /eventi/{id})
 * - [Il collega aggiungerà: GET /eventi con filtro data]
 *
 * @RestController:
 * - Combina @Controller + @ResponseBody
 * - Tutte le risposte sono automaticamente convertite in JSON
 *
 * @RequestMapping("/eventi"):
 * - Tutti gli endpoint di questo controller iniziano con /eventi
 * - Base URL: http://localhost:8080/eventi
 *
 * ARCHITETTURA:
 * Client → Controller → Service → Repository → Database
 *   ↓         ↓          ↓          ↓
 * JSON    Validazione  Business  Query SQL
 *         DTO          Logic
 *
 * FLUSSO TIPICO:
 * 1. Client invia richiesta HTTP (es: POST /eventi)
 * 2. Controller riceve e valida i dati (@Valid)
 * 3. Controller chiama il Service
 * 4. Service esegue la logica di business
 * 5. Service restituisce il risultato
 * 6. Controller converte in ResponseEntity e invia al client
 */
@RestController
@RequestMapping("/eventi")
public class EventoController {

    private final EventoService eventoService;

    /**
     * COSTRUTTORE PER DEPENDENCY INJECTION
     *
     * Spring inietta automaticamente EventoService
     */
    public EventoController(EventoService eventoService) {
        this.eventoService = eventoService;
    }

    /**
     * ENDPOINT: CREA UN NUOVO EVENTO
     *
     * POST /eventi
     *
     * Questo endpoint permette di creare un nuovo evento nel sistema.
     * Riceve un JSON con i dati dell'evento e restituisce l'evento creato con ID.
     *
     * @PostMapping:
     * - Gestisce richieste HTTP POST
     * - Riceve dati nel body della richiesta
     *
     * @RequestBody:
     * - Indica che i dati arrivano nel body come JSON
     * - Spring converte automaticamente JSON → EventoRequestDTO
     *
     * @Valid:
     * - Attiva la validazione automatica del DTO
     * - Se la validazione fallisce, Spring restituisce HTTP 400
     * - I messaggi di errore vengono gestiti da GlobalExceptionHandler
     *
     * ESEMPIO DI RICHIESTA:
     * POST http://localhost:8080/eventi
     * Content-Type: application/json
     *
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
     * ESEMPIO DI RISPOSTA (SUCCESS):
     * Status: 201 CREATED
     * Body:
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
     * POSSIBILI ERRORI:
     * - 400 BAD REQUEST: dati non validi (validazione fallita)
     * - 400 BAD REQUEST: tipologiaEventoId o clienteId non esistono
     * - 500 INTERNAL SERVER ERROR: errore imprevisto
     *
     * @param requestDTO I dati dell'evento da creare
     * @return ResponseEntity con l'evento creato e HTTP 201
     */
    @PostMapping
    public ResponseEntity<EventoResponseDTO> createEvento(
            @Valid @RequestBody EventoRequestDTO requestDTO) {

        // Chiama il service per creare l'evento
        EventoResponseDTO eventoCreato = eventoService.createEvento(requestDTO);

        // Restituisce HTTP 201 CREATED con l'evento creato
        return new ResponseEntity<>(eventoCreato, HttpStatus.CREATED);
    }

    /**
     * ENDPOINT: RECUPERA UN EVENTO PER ID
     *
     * GET /eventi/{id}
     *
     * Questo endpoint permette di recuperare i dettagli di un evento specifico
     * tramite il suo ID.
     *
     * @GetMapping("/{id}"):
     * - Gestisce richieste HTTP GET
     * - {id} è una path variable (parte dell'URL)
     *
     * @PathVariable:
     * - Estrae il valore di {id} dall'URL
     * - Spring lo converte automaticamente in Long
     *
     * ESEMPIO DI RICHIESTA:
     * GET http://localhost:8080/eventi/1
     *
     * ESEMPIO DI RISPOSTA (SUCCESS):
     * Status: 200 OK
     * Body:
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
     * ESEMPIO DI RISPOSTA (NOT FOUND):
     * Status: 404 NOT FOUND
     * Body:
     * {
     *   "timestamp": "2024-12-15T10:12:00",
     *   "status": 404,
     *   "error": "Not Found",
     *   "message": "Evento non trovato con ID: 999"
     * }
     *
     * POSSIBILI ERRORI:
     * - 404 NOT FOUND: evento con quell'ID non esiste
     * - 500 INTERNAL SERVER ERROR: errore imprevisto
     *
     * @param id L'ID dell'evento da recuperare
     * @return ResponseEntity con l'evento trovato e HTTP 200
     * @throws EventoNotFoundException se l'evento non esiste (gestito da GlobalExceptionHandler)
     */
    @GetMapping("/{id}")
    public ResponseEntity<EventoResponseDTO> getEventoById(@PathVariable Long id) {

        // Chiama il service per recuperare l'evento
        // Se non esiste, il service lancia EventoNotFoundException
        // che viene catturata da GlobalExceptionHandler → HTTP 404
        EventoResponseDTO evento = eventoService.getEventoById(id);

        // Restituisce HTTP 200 OK con l'evento
        return ResponseEntity.ok(evento);
    }

    // ========================================================================
    // NOTA PER IL COLLEGA (PERSONA 2)
    // ========================================================================

    /**
     * TODO: Il collega (Persona 2) aggiungerà qui l'endpoint:
     *
     * GET /eventi (con parametro opzionale ?data=2024-06-15)
     *
     * Questo endpoint dovrà:
     * - Restituire tutti gli eventi se nessun parametro
     * - Filtrare per data se parametro fornito
     * - Usare EventoService.getAllEventi() o getEventiByData(LocalDate)
     *
     * ESEMPIO:
     *
     * @GetMapping
     * public ResponseEntity<List<EventoResponseDTO>> getAllEventi(
     *         @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data) {
     *
     *     List<EventoResponseDTO> eventi;
     *
     *     if (data != null) {
     *         eventi = eventoService.getEventiByData(data);
     *     } else {
     *         eventi = eventoService.getAllEventi();
     *     }
     *
     *     return ResponseEntity.ok(eventi);
     * }
     */
}
