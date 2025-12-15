package com.eventpro.catering.service;

import com.eventpro.catering.dto.EventoRequestDTO;
import com.eventpro.catering.dto.EventoResponseDTO;
import com.eventpro.catering.exception.EventoNotFoundException;
import com.eventpro.catering.model.Cliente;
import com.eventpro.catering.model.Evento;
import com.eventpro.catering.model.TipologiaEvento;
import com.eventpro.catering.repository.ClienteRepository;
import com.eventpro.catering.repository.EventoRepository;
import com.eventpro.catering.repository.TipologiaEventoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * SERVICE PER LA GESTIONE DEGLI EVENTI
 *
 * Questo service contiene la logica di business per:
 * - Creare nuovi eventi
 * - Recuperare eventi dal database
 * - Validare i dati
 * - Convertire tra Entity e DTO
 *
 * @Service:
 * - Dice a Spring che questa è una classe di business logic
 * - Viene automaticamente creata e gestita da Spring (singleton)
 *
 * @Transactional:
 * - Gestisce le transazioni del database
 * - Se un'operazione fallisce, fa rollback automatico
 * - Garantisce consistenza dei dati
 *
 * PERCHÉ USARE UN SERVICE:
 * 1. Separazione delle responsabilità (Controller → Service → Repository)
 * 2. Riutilizzabilità della logica di business
 * 3. Testabilità (possiamo testare il service separatamente)
 * 4. Gestione transazioni
 */
@Service
@Transactional
public class EventoService {

    private final EventoRepository eventoRepository;
    private final TipologiaEventoRepository tipologiaEventoRepository;
    private final ClienteRepository clienteRepository;

    /**
     * COSTRUTTORE PER DEPENDENCY INJECTION
     *
     * Spring inietta automaticamente i repository necessari
     */
    public EventoService(EventoRepository eventoRepository,
                         TipologiaEventoRepository tipologiaEventoRepository,
                         ClienteRepository clienteRepository) {
        this.eventoRepository = eventoRepository;
        this.tipologiaEventoRepository = tipologiaEventoRepository;
        this.clienteRepository = clienteRepository;
    }

    /**
     * CREA UN NUOVO EVENTO
     *
     * Questo metodo:
     * 1. Valida i dati in input (orari, relazioni)
     * 2. Recupera TipologiaEvento e Cliente dal database
     * 3. Crea l'entity Evento
     * 4. Salva nel database
     * 5. Converte in DTO e restituisce
     *
     * @param requestDTO I dati per creare l'evento
     * @return EventoResponseDTO con i dati dell'evento creato
     * @throws IllegalArgumentException se i dati non sono validi
     *
     * ESEMPIO DI USO:
     * EventoRequestDTO request = new EventoRequestDTO(...);
     * EventoResponseDTO response = eventoService.createEvento(request);
     * // response contiene l'evento con ID generato
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

        // VALIDAZIONE 3: Recupera Cliente (se fornito)
        Cliente cliente = null;
        if (requestDTO.getClienteId() != null) {
            cliente = clienteRepository
                    .findById(requestDTO.getClienteId())
                    .orElseThrow(() -> new IllegalArgumentException(
                            "Cliente non trovato con ID: " + requestDTO.getClienteId()
                    ));
        }

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
        evento.setCliente(cliente);

        // SALVATAGGIO: Salva nel database
        Evento eventoSalvato = eventoRepository.save(evento);

        // CONVERSIONE: Entity → DTO per la risposta
        return convertToResponseDTO(eventoSalvato);
    }

    /**
     * RECUPERA UN EVENTO PER ID
     *
     * Questo metodo:
     * 1. Cerca l'evento nel database per ID
     * 2. Se non esiste, lancia EventoNotFoundException
     * 3. Converte in DTO e restituisce
     *
     * @param id L'ID dell'evento da recuperare
     * @return EventoResponseDTO con i dati dell'evento
     * @throws EventoNotFoundException se l'evento non esiste
     *
     * ESEMPIO DI USO:
     * EventoResponseDTO evento = eventoService.getEventoById(1L);
     * // evento contiene tutti i dati incluse relazioni (tipologia, cliente)
     */
    public EventoResponseDTO getEventoById(Long id) {
        Evento evento = eventoRepository.findById(id)
                .orElseThrow(() -> new EventoNotFoundException(id));

        return convertToResponseDTO(evento);
    }

    /**
     * CONVERTE ENTITY → DTO
     *
     * Metodo helper privato che converte un'entity Evento
     * in un DTO EventoResponseDTO pronto per essere restituito al client.
     *
     * Gestisce anche la conversione delle relazioni (TipologiaEvento e Cliente)
     * in nested objects del DTO.
     *
     * @param evento L'entity da convertire
     * @return EventoResponseDTO
     */
    private EventoResponseDTO convertToResponseDTO(Evento evento) {
        // Crea DTO per TipologiaEvento (sempre presente)
        EventoResponseDTO.TipologiaEventoDTO tipologiaDTO =
                new EventoResponseDTO.TipologiaEventoDTO(
                        evento.getTipologiaEvento().getId(),
                        evento.getTipologiaEvento().getNome()
                );

        // Crea DTO per Cliente (solo se presente)
        EventoResponseDTO.ClienteDTO clienteDTO = null;
        if (evento.getCliente() != null) {
            clienteDTO = new EventoResponseDTO.ClienteDTO(
                    evento.getCliente().getId(),
                    evento.getCliente().getNome(),
                    evento.getCliente().getCognome()
            );
        }

        // Crea e restituisce il DTO completo
        return new EventoResponseDTO(
                evento.getId(),
                evento.getNome(),
                evento.getData(),
                evento.getOraInizio(),
                evento.getOraFine(),
                evento.getLocation(),
                evento.getNote(),
                evento.getEventoGiornaliero(),
                tipologiaDTO,
                clienteDTO
        );
    }
}
