package com.eventpro.catering.service;

import com.eventpro.catering.dto.EventoRequestDTO;
import com.eventpro.catering.dto.EventoResponseDTO;
import com.eventpro.catering.exception.EventoNotFoundException;
import com.eventpro.catering.model.Evento;
import com.eventpro.catering.model.TipologiaEvento;
import com.eventpro.catering.repository.EventoRepository;
import com.eventpro.catering.repository.TipologiaEventoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**
 * SERVICE PER LA GESTIONE DEGLI EVENTI
 *
 * Versione SEMPLIFICATA - solo gestione evento, senza cliente
 */
@Service
@Transactional
public class EventoService {

    private final EventoRepository eventoRepository;
    private final TipologiaEventoRepository tipologiaEventoRepository;

    public EventoService(EventoRepository eventoRepository,
                         TipologiaEventoRepository tipologiaEventoRepository) {
        this.eventoRepository = eventoRepository;
        this.tipologiaEventoRepository = tipologiaEventoRepository;
    }

    /**
     * CREA UN NUOVO EVENTO
     */
    public EventoResponseDTO createEvento(EventoRequestDTO requestDTO) {
        // VALIDAZIONE 1: Verifica che ora fine sia dopo ora inizio
        if (requestDTO.getOraFine().isBefore(requestDTO.getOraInizio()) ||
                requestDTO.getOraFine().equals(requestDTO.getOraInizio())) {
            throw new IllegalArgumentException(
                    "L'ora di fine deve essere successiva all'ora di inizio"
            );
        }

        // VALIDAZIONE 2: Recupera e verifica TipologiaEvento
        TipologiaEvento tipologia = tipologiaEventoRepository
                .findById(requestDTO.getTipologiaEventoId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "TipologiaEvento non trovata con ID: " + requestDTO.getTipologiaEventoId()
                ));

        // CREAZIONE ENTITY: Converte DTO → Entity
        Evento evento = new Evento();
        evento.setNome(requestDTO.getNome());
        evento.setData(requestDTO.getData());
        evento.setOraInizio(requestDTO.getOraInizio());
        evento.setOraFine(requestDTO.getOraFine());
        evento.setLocation(requestDTO.getLocation());
        evento.setNote(requestDTO.getNote());
        evento.setEventoGiornaliero(requestDTO.getEventoGiornaliero());
        evento.setTipologiaEvento(tipologia);
        // Cliente rimosso - solo gestione eventi

        // SALVATAGGIO: Salva nel database
        Evento eventoSalvato = eventoRepository.save(evento);

        // CONVERSIONE: Entity → DTO per la risposta
        return convertToResponseDTO(eventoSalvato);
    }

    /**
     * RECUPERA UN EVENTO PER ID
     */
    public EventoResponseDTO getEventoById(Long id) {
        Evento evento = eventoRepository.findById(id)
                .orElseThrow(() -> new EventoNotFoundException(id));

        return convertToResponseDTO(evento);
    }

    /**
     * RECUPERA TUTTI GLI EVENTI
     *
     * Questo metodo restituisce tutti gli eventi presenti nel database
     * ordinati per data crescente.
     *
     * COME FUNZIONA:
     * 1. Chiama il repository per recuperare tutti gli eventi ordinati
     * 2. Usa stream() per convertire ogni Evento in EventoResponseDTO
     * 3. Raccoglie i risultati in una lista
     *
     * ESEMPIO DI USO:
     * List<EventoResponseDTO> eventi = eventoService.getAllEventi();
     * // eventi contiene tutti gli eventi del database
     *
     * @return Lista di EventoResponseDTO ordinati per data
     */
    public List<EventoResponseDTO> getAllEventi() {
        // STEP 1: Recupera tutti gli eventi dal repository ordinati per data
        List<Evento> eventi = eventoRepository.findAllByOrderByDataAsc();

        // STEP 2: Converte ogni Evento in EventoResponseDTO usando stream
        // - stream(): crea uno stream dalla lista
        // - map(this::convertToResponseDTO): applica la conversione a ogni elemento
        // - collect(Collectors.toList()): raccoglie i risultati in una lista
        return eventi.stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    /**
     * RECUPERA EVENTI PER DATA
     *
     * Questo metodo restituisce solo gli eventi che si svolgono
     * in una specifica data.
     *
     * COME FUNZIONA:
     * 1. Chiama il repository per recuperare eventi filtrati per data
     * 2. Usa stream() per convertire ogni Evento in EventoResponseDTO
     * 3. Raccoglie i risultati in una lista
     *
     * ESEMPIO DI USO:
     * LocalDate data = LocalDate.of(2024, 6, 15);
     * List<EventoResponseDTO> eventiDelGiorno = eventoService. getEventiByData(data);
     * // eventiDelGiorno contiene solo eventi del 15 giugno 2024
     *
     * @param data La data da filtrare (es: 2024-06-15)
     * @return Lista di EventoResponseDTO per quella data
     */
    public List<EventoResponseDTO> getEventiByData(LocalDate data) {
        // STEP 1: Recupera eventi filtrati per data dal repository
        List<Evento> eventi = eventoRepository.findByData(data);

        // STEP 2: Converte ogni Evento in EventoResponseDTO usando stream
        return eventi.stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors. toList());
    }

    /**
     * CONVERTE ENTITY → DTO
     */
    private EventoResponseDTO convertToResponseDTO(Evento evento) {
        // Crea DTO per TipologiaEvento
        EventoResponseDTO.TipologiaEventoDTO tipologiaDTO =
                new EventoResponseDTO.TipologiaEventoDTO(
                        evento.getTipologiaEvento().getId(),
                        evento.getTipologiaEvento().getNome()
                );

        // Crea e restituisce il DTO completo (senza cliente)
        return new EventoResponseDTO(
                evento.getId(),
                evento.getNome(),
                evento.getData(),
                evento.getOraInizio(),
                evento.getOraFine(),
                evento.getLocation(),
                evento.getNote(),
                evento.getEventoGiornaliero(),
                tipologiaDTO
        );
    }
}
