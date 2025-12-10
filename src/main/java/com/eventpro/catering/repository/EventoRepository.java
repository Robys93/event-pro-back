package com.eventpro.catering.repository;

import com.eventpro.catering.model.Evento;
import com.eventpro.catering. model.TipologiaEvento;
import com.eventpro.catering. model.Cliente;
import org. springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java. util.List;
import java.util.Optional;

/**
 * REPOSITORY EVENTO - INTERFACE PER ACCEDERE AI DATI DEGLI EVENTI
 *
 * Un Repository è un'interfaccia che Spring usa per gestire l'accesso ai dati.
 * Invece di scrivere query SQL manualmente, il Repository fa tutto automaticamente.
 *
 * COME FUNZIONA:
 * 1. Estendi JpaRepository<Evento, Long>
 *    - Evento: è la classe entity che gestisci
 *    - Long: è il tipo dell'ID (la chiave primaria)
 * 2. JpaRepository ti fornisce automaticamente metodi come:
 *    - save(evento): salva un evento nel database
 *    - findById(id): trova un evento per ID
 *    - findAll(): restituisce tutti gli eventi
 *    - delete(evento): elimina un evento
 *    - count(): conta quanti eventi ci sono
 * 3. Non devi implementare nulla, Spring lo fa per te!
 *
 * PERCHÉ SERVE @Repository:
 * - È un'annotazione che dice a Spring di gestire questa interfaccia
 * - Spring crea automaticamente un'implementazione del Repository
 * - Puoi poi usarlo in altre classi (Service, Controller) tramite l'iniezione di dipendenze
 */
@Repository
public interface EventoRepository extends JpaRepository<Evento, Long> {

    /**
     * TROVA EVENTI PER NOME
     *
     * Usa la convenzione di denominazione di Spring Data JPA per creare automaticamente
     * una query SQL che cerca eventi in base al nome.
     *
     * QUERY SQL GENERATA AUTOMATICAMENTE:
     * SELECT * FROM evento WHERE nome = ?
     *
     * COSA RESTITUISCE:
     * - List<Evento>: una lista di eventi con quel nome (può essere vuota)
     *
     * ESEMPIO DI USO:
     * List<Evento> eventi = eventoRepository.findByNome("Matrimonio Mario e Lucia");
     * if (!eventi.isEmpty()) {
     *     System.out.println("Trovati " + eventi.size() + " eventi con questo nome");
     * }
     *
     * @param nome Il nome dell'evento da cercare
     * @return Lista di eventi con quel nome
     */
    List<Evento> findByNome(String nome);

    /**
     * TROVA EVENTI PER DATA
     *
     * Cerca tutti gli eventi che si svolgono in una data specifica.
     *
     * QUERY SQL GENERATA AUTOMATICAMENTE:
     * SELECT * FROM evento WHERE data = ?
     *
     * ESEMPIO DI USO:
     * LocalDate data = LocalDate.of(2024, 6, 15);
     * List<Evento> eventiDelGiorno = eventoRepository.findByData(data);
     * System.out.println("Eventi del 15 giugno 2024: " + eventiDelGiorno.size());
     *
     * @param data La data da cercare
     * @return Lista di eventi in quella data
     */
    List<Evento> findByData(LocalDate data);

    /**
     * TROVA EVENTI PER TIPOLOGIA
     *
     * Cerca tutti gli eventi di una specifica tipologia.
     *
     * QUERY SQL GENERATA AUTOMATICAMENTE:
     * SELECT * FROM evento WHERE tipologia_evento_id = ?
     *
     * ESEMPIO DI USO:
     * TipologiaEvento matrimonio = tipologiaEventoRepository.findByNome("Matrimonio").get();
     * List<Evento> matrimoni = eventoRepository.findByTipologiaEvento(matrimonio);
     * System.out.println("Totale matrimoni: " + matrimoni.size());
     *
     * @param tipologiaEvento La tipologia da cercare
     * @return Lista di eventi di quella tipologia
     */
    List<Evento> findByTipologiaEvento(TipologiaEvento tipologiaEvento);

    /**
     * TROVA EVENTI PER CLIENTE
     *
     * Cerca tutti gli eventi collegati a un cliente specifico.
     *
     * QUERY SQL GENERATA AUTOMATICAMENTE:
     * SELECT * FROM evento WHERE cliente_id = ?
     *
     * ESEMPIO DI USO:
     * Cliente cliente = clienteRepository.findById(5L).get();
     * List<Evento> eventiCliente = eventoRepository.findByCliente(cliente);
     * System.out.println("Eventi del cliente " + cliente.getNome() + ": " + eventiCliente.size());
     *
     * @param cliente Il cliente da cercare
     * @return Lista di eventi di quel cliente
     */
    List<Evento> findByCliente(Cliente cliente);

    /**
     * TROVA EVENTI GIORNALIERI
     *
     * Cerca tutti gli eventi che durano tutto il giorno.
     *
     * QUERY SQL GENERATA AUTOMATICAMENTE:
     * SELECT * FROM evento WHERE evento_giornaliero = ?
     *
     * ESEMPIO DI USO:
     * List<Evento> eventiGiornalieri = eventoRepository. findByEventoGiornaliero(true);
     * System.out.println("Eventi giornalieri: " + eventiGiornalieri.size());
     *
     * @param eventoGiornaliero true per eventi giornalieri, false per eventi con orari specifici
     * @return Lista di eventi filtrati per flag giornaliero
     */
    List<Evento> findByEventoGiornaliero(Boolean eventoGiornaliero);

    /**
     * TROVA EVENTI IN UN RANGE DI DATE
     *
     * Cerca tutti gli eventi che si svolgono tra due date (incluse).
     *
     * QUERY SQL GENERATA AUTOMATICAMENTE:
     * SELECT * FROM evento WHERE data BETWEEN ? AND ?
     *
     * ESEMPIO DI USO:
     * LocalDate inizio = LocalDate.of(2024, 6, 1);
     * LocalDate fine = LocalDate.of(2024, 6, 30);
     * List<Evento> eventiGiugno = eventoRepository.findByDataBetween(inizio, fine);
     * System.out.println("Eventi di giugno 2024: " + eventiGiugno.size());
     *
     * @param dataInizio Data di inizio del range
     * @param dataFine Data di fine del range
     * @return Lista di eventi nel range di date
     */
    List<Evento> findByDataBetween(LocalDate dataInizio, LocalDate dataFine);

    /**
     * TROVA EVENTI PER LOCATION (CONTIENE TESTO)
     *
     * Cerca eventi la cui location contiene una specifica stringa.
     * Utile per cercare eventi in una città o luogo specifico.
     *
     * QUERY SQL GENERATA AUTOMATICAMENTE:
     * SELECT * FROM evento WHERE location LIKE %?%
     *
     * ESEMPIO DI USO:
     * List<Evento> eventiRoma = eventoRepository.findByLocationContaining("Roma");
     * System.out.println("Eventi a Roma: " + eventiRoma.size());
     *
     * @param location Testo da cercare nella location
     * @return Lista di eventi con location che contiene il testo
     */
    List<Evento> findByLocationContaining(String location);

    /**
     * VERIFICA SE ESISTONO EVENTI IN UNA DATA
     *
     * Controlla se ci sono eventi in una data specifica.
     *
     * QUERY SQL GENERATA AUTOMATICAMENTE:
     * SELECT COUNT(*) > 0 FROM evento WHERE data = ?
     *
     * ESEMPIO DI USO:
     * LocalDate data = LocalDate.of(2024, 12, 25);
     * boolean ciSonoEventi = eventoRepository.existsByData(data);
     * if (ciSonoEventi) {
     *     System.out.println("Ci sono eventi il 25 dicembre!");
     * }
     *
     * @param data La data da verificare
     * @return true se esistono eventi in quella data, false altrimenti
     */
    boolean existsByData(LocalDate data);

    /**
     * CONTA EVENTI PER TIPOLOGIA
     *
     * Conta quanti eventi ci sono di una specifica tipologia.
     *
     * QUERY SQL GENERATA AUTOMATICAMENTE:
     * SELECT COUNT(*) FROM evento WHERE tipologia_evento_id = ?
     *
     * ESEMPIO DI USO:
     * TipologiaEvento compleanno = tipologiaEventoRepository.findByNome("Compleanno").get();
     * long totale = eventoRepository.countByTipologiaEvento(compleanno);
     * System.out.println("Totale compleanni: " + totale);
     *
     * @param tipologiaEvento La tipologia da contare
     * @return Numero di eventi di quella tipologia
     */
    long countByTipologiaEvento(TipologiaEvento tipologiaEvento);

    /**
     * TROVA EVENTI ORDINATI PER DATA (CRESCENTE)
     *
     * Restituisce tutti gli eventi ordinati per data, dal più vicino al più lontano.
     *
     * QUERY SQL GENERATA AUTOMATICAMENTE:
     * SELECT * FROM evento ORDER BY data ASC
     *
     * ESEMPIO DI USO:
     * List<Evento> eventiOrdinati = eventoRepository.findAllByOrderByDataAsc();
     * System.out.println("Prossimo evento: " + eventiOrdinati.get(0).getNome());
     *
     * @return Lista di tutti gli eventi ordinati per data crescente
     */
    List<Evento> findAllByOrderByDataAsc();

    /**
     * TROVA EVENTI ORDINATI PER DATA (DECRESCENTE)
     *
     * Restituisce tutti gli eventi ordinati per data, dal più lontano al più vicino.
     *
     * QUERY SQL GENERATA AUTOMATICAMENTE:
     * SELECT * FROM evento ORDER BY data DESC
     *
     * @return Lista di tutti gli eventi ordinati per data decrescente
     */
    List<Evento> findAllByOrderByDataDesc();

    // ========================================================================
    // METODI EREDITATI DA JpaRepository (già disponibili automaticamente):
    // ========================================================================
    // - save(evento) → salva o aggiorna un evento
    // - findById(id) → trova un evento per ID
    // - findAll() → restituisce tutti gli eventi
    // - deleteById(id) → elimina un evento per ID
    // - delete(evento) → elimina un evento
    // - count() → conta il totale degli eventi
    // - existsById(id) → verifica se esiste un evento con quell'ID
    // ========================================================================
}