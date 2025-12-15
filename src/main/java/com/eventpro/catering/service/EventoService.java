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
