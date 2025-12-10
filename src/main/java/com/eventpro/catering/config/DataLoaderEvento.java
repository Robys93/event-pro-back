package com.eventpro.catering.config;

import com.eventpro.catering.model.Evento;
import com.eventpro.catering.model.TipologiaEvento;
import com.eventpro.catering.repository.EventoRepository;
import com.eventpro.catering.repository.TipologiaEventoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation. Configuration;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * DATA LOADER PER EVENTO
 *
 * Questa classe carica automaticamente dati di esempio nel database all'avvio dell'applicazione.
 * È utile per:
 * - Testing e sviluppo
 * - Avere dati iniziali nel database
 * - Verificare che le entità e i repository funzionino correttamente
 *
 * COME FUNZIONA:
 * 1. All'avvio dell'applicazione, Spring esegue il metodo definito in @Bean CommandLineRunner
 * 2. Il metodo riceve i repository tramite dependency injection
 * 3. Controlla se esistono già dati nel database
 * 4. Se il database è vuoto, inserisce gli eventi di esempio
 *
 * IMPORTANTE:
 * - Questo DataLoader viene eseguito DOPO DataLoaderTipologiaEvento
 * - Verifica che le tipologie esistano prima di creare eventi
 * - Per ora, tutti gli eventi hanno cliente = null
 *
 * ANNOTAZIONI:
 * - @Configuration: dice a Spring che questa classe contiene configurazioni
 * - @Bean: dice a Spring di eseguire questo metodo all'avvio
 * - CommandLineRunner: interfaccia funzionale che esegue codice dopo l'avvio di Spring Boot
 */
@Configuration
public class DataLoaderEvento {

    /**
     * CARICA DATI DI ESEMPIO NEL DATABASE
     *
     * Questo metodo viene eseguito automaticamente all'avvio dell'applicazione.
     * Inserisce alcuni eventi di esempio se il database è vuoto.
     *
     * @param eventoRepository Il repository per gestire gli eventi
     * @param tipologiaEventoRepository Il repository per recuperare le tipologie
     * @return CommandLineRunner che esegue la logica di caricamento
     */
    @Bean
    CommandLineRunner initDatabaseEvento(EventoRepository eventoRepository,
                                         TipologiaEventoRepository tipologiaEventoRepository) {
        return args -> {
            // Conta quanti eventi esistono già nel database
            long count = eventoRepository.count();

            // Se il database è vuoto, inserisci dati di esempio
            if (count == 0) {
                System.out.println("========================================");
                System.out.println("DATABASE EVENTO VUOTO");
                System.out.println("Caricamento dati di esempio...");
                System.out.println("========================================");

                // ====================================================================
                // VERIFICA CHE LE TIPOLOGIE ESISTANO
                // ====================================================================
                // Prima di creare eventi, dobbiamo verificare che le tipologie esistano
                // (create da DataLoaderTipologiaEvento)

                long countTipologie = tipologiaEventoRepository. count();
                if (countTipologie == 0) {
                    System.out.println("⚠️ ATTENZIONE: Nessuna tipologia trovata nel database!");
                    System.out.println("⚠️ Esegui prima DataLoaderTipologiaEvento!");
                    System.out.println("========================================\n");
                    return; // Esce senza creare eventi
                }

                // ====================================================================
                // RECUPERA LE TIPOLOGIE DAL DATABASE
                // ====================================================================

                TipologiaEvento matrimonio = tipologiaEventoRepository.findByNome("Matrimonio")
                        .orElse(null);
                TipologiaEvento compleanno = tipologiaEventoRepository.findByNome("Compleanno")
                        .orElse(null);
                TipologiaEvento aziendale = tipologiaEventoRepository.findByNome("Evento aziendale")
                        .orElse(null);
                TipologiaEvento conferenza = tipologiaEventoRepository.findByNome("Conferenza")
                        .orElse(null);
                TipologiaEvento festaPrivata = tipologiaEventoRepository.findByNome("Festa privata")
                        .orElse(null);

                // ====================================================================
                // CREA EVENTI DI ESEMPIO
                // ====================================================================

                // EVENTO 1: Matrimonio Mario e Lucia
                if (matrimonio != null) {
                    Evento evento1 = new Evento(
                            "Matrimonio Mario Rossi e Lucia Bianchi",
                            LocalDate.of(2024, 6, 15), // 15 giugno 2024
                            LocalTime.of(18, 0), // 18:00
                            LocalTime.of(23, 30), // 23:30
                            "Villa Roma, Via dei Fiori 123, Roma",
                            "Menu vegetariano per 5 ospiti. Allestimento con fiori bianchi e rossi. Torta nuziale a 3 piani.",
                            false, // Non è un evento giornaliero
                            matrimonio,
                            null // Nessun cliente
                    );
                    eventoRepository.save(evento1);
                    System.out.println("✓ Salvato evento: " + evento1.getNome());
                }

                // EVENTO 2: Compleanno 50 anni Giovanni
                if (compleanno != null) {
                    Evento evento2 = new Evento(
                            "Compleanno 50 anni Giovanni Verdi",
                            LocalDate. of(2024, 8, 20), // 20 agosto 2024
                            LocalTime.of(20, 0), // 20:00
                            LocalTime. of(1, 0), // 01:00
                            "Ristorante Da Carlo, Piazza Garibaldi 45, Milano",
                            "Buffet con antipasti misti, primo, secondo e dolce. Animazione con DJ.",
                            false,
                            compleanno,
                            null
                    );
                    eventoRepository.save(evento2);
                    System.out.println("✓ Salvato evento: " + evento2.getNome());
                }

                // EVENTO 3: Conferenza Annuale Azienda XYZ (Evento Giornaliero)
                if (conferenza != null) {
                    Evento evento3 = new Evento(
                            "Conferenza Annuale Azienda XYZ 2024",
                            LocalDate.of(2024, 10, 5), // 5 ottobre 2024
                            LocalTime.of(9, 0), // 09:00
                            LocalTime.of(18, 0), // 18:00
                            "Hotel Marriott, Sala Conferenze A, Via Veneto 88, Roma",
                            "Coffee break alle 10:30 e 16:00. Pranzo alle 13:00. Richiesta proiettore, microfono e lavagna.",
                            true, // Evento giornaliero
                            conferenza,
                            null
                    );
                    eventoRepository.save(evento3);
                    System.out.println("✓ Salvato evento: " + evento3.getNome());
                }

                // EVENTO 4: Team Building Aziendale
                if (aziendale != null) {
                    Evento evento4 = new Evento(
                            "Team Building Aziendale - Gruppo ABC",
                            LocalDate.of(2024, 9, 12), // 12 settembre 2024
                            LocalTime.of(10, 0), // 10:00
                            LocalTime. of(17, 0), // 17:00
                            "Agriturismo Il Casale, Strada Provinciale 34, Frascati",
                            "Attività outdoor: orienteering, giochi di squadra. Pranzo BBQ. 30 partecipanti.",
                            true,
                            aziendale,
                            null
                    );
                    eventoRepository.save(evento4);
                    System.out.println("✓ Salvato evento: " + evento4.getNome());
                }

                // EVENTO 5: Festa di Laurea
                if (festaPrivata != null) {
                    Evento evento5 = new Evento(
                            "Festa di Laurea Francesca Neri",
                            LocalDate. of(2024, 7, 10), // 10 luglio 2024
                            LocalTime.of(19, 30), // 19:30
                            LocalTime.of(23, 0), // 23:00
                            "Terrazza Belvedere, Via Panoramica 56, Napoli",
                            "Aperitivo con finger food. Musica dal vivo. Vista mare.",
                            false,
                            festaPrivata,
                            null
                    );
                    eventoRepository.save(evento5);
                    System. out.println("✓ Salvato evento: " + evento5.getNome());
                }

                // EVENTO 6: Meeting Trimestrale (senza note)
                if (aziendale != null) {
                    Evento evento6 = new Evento(
                            "Meeting Trimestrale Q3 2024",
                            LocalDate.of(2024, 9, 30), // 30 settembre 2024
                            LocalTime.of(14, 0), // 14:00
                            LocalTime.of(16, 30), // 16:30
                            "Sede Aziendale, Sala Riunioni B, Via Torino 12, Milano",
                            null, // Nessuna nota
                            false,
                            aziendale,
                            null
                    );
                    eventoRepository.save(evento6);
                    System.out. println("✓ Salvato evento: " + evento6.getNome());
                }

                // EVENTO 7: Cena di Gala
                if (festaPrivata != null) {
                    Evento evento7 = new Evento(
                            "Cena di Gala - Raccolta Fondi Beneficenza",
                            LocalDate. of(2024, 11, 22), // 22 novembre 2024
                            LocalTime.of(20, 30), // 20:30
                            LocalTime.of(0, 0), // 00:00
                            "Grand Hotel Palace, Salone delle Feste, Piazza della Repubblica 10, Firenze",
                            "Dress code: elegante. Menu gourmet 5 portate. Asta benefica durante la serata.",
                            false,
                            festaPrivata,
                            null
                    );
                    eventoRepository. save(evento7);
                    System.out.println("✓ Salvato evento: " + evento7.getNome());
                }

                // EVENTO 8: Compleanno Bambini
                if (compleanno != null) {
                    Evento evento8 = new Evento(
                            "Festa di Compleanno 8 anni - Matteo",
                            LocalDate.of(2024, 12, 3), // 3 dicembre 2024
                            LocalTime.of(15, 0), // 15:00
                            LocalTime. of(18, 0), // 18:00
                            "Ludoteca Arcobaleno, Via dei Bambini 7, Bologna",
                            "Animazione con clown e giochi. Torta tema supereroi. 20 bambini + genitori.",
                            false,
                            compleanno,
                            null
                    );
                    eventoRepository.save(evento8);
                    System.out.println("✓ Salvato evento: " + evento8.getNome());
                }

                System.out.println("========================================");
                System.out.println("✓ DATI CARICATI CON SUCCESSO!");
                System.out.println("Totale eventi inseriti: " + eventoRepository.count());
                System.out.println("========================================");

                // ====================================================================
                // STAMPA RIEPILOGO EVENTI PER TIPOLOGIA
                // ====================================================================

                System.out.println("\n========================================");
                System.out.println("RIEPILOGO EVENTI PER TIPOLOGIA:");
                System.out.println("========================================");

                if (matrimonio != null) {
                    long countMatrimoni = eventoRepository.countByTipologiaEvento(matrimonio);
                    System.out.println("  - Matrimoni: " + countMatrimoni);
                }

                if (compleanno != null) {
                    long countCompleanni = eventoRepository.countByTipologiaEvento(compleanno);
                    System.out.println(" - Compleanni: " + countCompleanni);
                }

                if (aziendale != null) {
                    long countAziendali = eventoRepository.countByTipologiaEvento(aziendale);
                    System. out.println(" - Eventi aziendali: " + countAziendali);
                }

                if (conferenza != null) {
                    long countConferenze = eventoRepository.countByTipologiaEvento(conferenza);
                    System. out.println(" - Conferenze: " + countConferenze);
                }

                if (festaPrivata != null) {
                    long countFestePrivate = eventoRepository.countByTipologiaEvento(festaPrivata);
                    System.out.println(" - Feste private: " + countFestePrivate);
                }

                System.out.println("========================================");

                // ====================================================================
                // STAMPA LISTA COMPLETA EVENTI
                // ====================================================================

                System.out.println("\nELENCO COMPLETO EVENTI NEL DATABASE:");
                System.out.println("========================================");
                eventoRepository.findAllByOrderByDataAsc().forEach(e ->
                        System.out.println(" - [" + e.getData() + "] " + e.getNome() +
                                " (" + e.getTipologiaEvento().getNome() + ")")
                );
                System.out.println("========================================\n");

            } else {
                System.out.println("========================================");
                System.out. println("DATABASE EVENTO GIÀ POPOLATO");
                System.out.println("Trovati " + count + " eventi esistenti.");
                System.out.println("========================================\n");
            }
        };
    }
}