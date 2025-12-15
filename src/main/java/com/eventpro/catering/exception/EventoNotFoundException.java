package com.eventpro.catering.exception;

/**
 * EXCEPTION PERSONALIZZATA - EVENTO NON TROVATO
 *
 * Questa exception viene lanciata quando si cerca di accedere a un evento
 * che non esiste nel database.
 *
 * QUANDO VIENE LANCIATA:
 * - GET /eventi/{id} → se l'ID non esiste
 * - Qualsiasi operazione che richiede un evento esistente
 *
 * ESEMPIO DI USO NEL SERVICE:
 * Evento evento = eventoRepository.findById(id)
 *     .orElseThrow(() -> new EventoNotFoundException(id));
 *
 * GESTIONE:
 * Verrà catturata dal GlobalExceptionHandler che restituirà HTTP 404
 *
 * EXTENDS RuntimeException:
 * - È un'unchecked exception (non richiede try-catch obbligatorio)
 * - Può essere lanciata senza dichiarazione nel metodo
 */
public class EventoNotFoundException extends RuntimeException {

    /**
     * COSTRUTTORE CON ID
     *
     * Crea un messaggio di errore che include l'ID dell'evento non trovato
     *
     * @param id L'ID dell'evento che non è stato trovato
     *
     * ESEMPIO:
     * throw new EventoNotFoundException(999L);
     * → Messaggio: "Evento non trovato con ID: 999"
     */
    public EventoNotFoundException(Long id) {
        super("Evento non trovato con ID: " + id);
    }

    /**
     * COSTRUTTORE CON MESSAGGIO PERSONALIZZATO
     *
     * Permette di specificare un messaggio di errore custom
     *
     * @param message Il messaggio di errore personalizzato
     *
     * ESEMPIO:
     * throw new EventoNotFoundException("Nessun evento trovato per la data specificata");
     */
    public EventoNotFoundException(String message) {
        super(message);
    }
}
