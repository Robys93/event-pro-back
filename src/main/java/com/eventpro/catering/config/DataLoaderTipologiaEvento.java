package com.eventpro.catering.config;

import com.eventpro.catering.model.TipologiaEvento;
import com.eventpro.catering.repository.TipologiaEventoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * DATA LOADER PER TIPOLOGIA_EVENTO
 *
 * Questa classe carica automaticamente dati di esempio nel database all'avvio dell'applicazione.
 * È utile per:
 * - Testing e sviluppo
 * - Avere dati iniziali nel database
 * - Verificare che le entità e i repository funzionino correttamente
 *
 * COME FUNZIONA:
 * 1. All'avvio dell'applicazione, Spring esegue il metodo definito in @Bean CommandLineRunner
 * 2. Il metodo riceve il repository tramite dependency injection
 * 3. Controlla se esistono già dati nel database
 * 4. Se il database è vuoto, inserisce le tipologie di esempio
 *
 * ANNOTAZIONI:
 * - @Configuration: dice a Spring che questa classe contiene configurazioni
 * - @Bean: dice a Spring di eseguire questo metodo all'avvio
 * - CommandLineRunner: interfaccia funzionale che esegue codice dopo l'avvio di Spring Boot
 */
@Configuration
public class DataLoaderTipologiaEvento {

    /**
     * CARICA DATI DI ESEMPIO NEL DATABASE
     *
     * Questo metodo viene eseguito automaticamente all'avvio dell'applicazione.
     * Inserisce alcune tipologie di evento se il database è vuoto.
     *
     * @param tipologiaEventoRepository Il repository per gestire le tipologie
     * @return CommandLineRunner che esegue la logica di caricamento
     */
    @Bean
    CommandLineRunner initDatabaseTipologiaEvento(TipologiaEventoRepository tipologiaEventoRepository) {
        return args -> {
            // Conta quante tipologie esistono già nel database
            long count = tipologiaEventoRepository.count();

            // Se il database è vuoto, inserisci dati di esempio
            if (count == 0) {
                System.out.println("========================================");
                System.out.println("DATABASE TIPOLOGIA_EVENTO VUOTO");
                System.out.println("Caricamento dati di esempio...");
                System.out.println("========================================");

                // Crea e salva diverse tipologie di evento
                TipologiaEvento matrimonio = new TipologiaEvento(
                        "Matrimonio",
                        "Eventi di matrimonio con servizio catering completo, allestimenti e coordinamento"
                );
                tipologiaEventoRepository.save(matrimonio);
                System.out.println("✓ Salvata tipologia: " + matrimonio.getNome());

                TipologiaEvento compleanno = new TipologiaEvento(
                        "Compleanno",
                        "Feste di compleanno per adulti e bambini con buffet e animazione"
                );
                tipologiaEventoRepository.save(compleanno);
                System.out.println("✓ Salvata tipologia: " + compleanno.getNome());

                TipologiaEvento aziendale = new TipologiaEvento(
                        "Evento aziendale",
                        "Eventi corporate, meeting, conferenze e team building"
                );
                tipologiaEventoRepository.save(aziendale);
                System.out.println("✓ Salvata tipologia: " + aziendale.getNome());

                TipologiaEvento conferenza = new TipologiaEvento(
                        "Conferenza",
                        "Conferenze, seminari e workshop con servizio coffee break e lunch"
                );
                tipologiaEventoRepository.save(conferenza);
                System.out.println("✓ Salvata tipologia: " + conferenza.getNome());

                TipologiaEvento festaPrivata = new TipologiaEvento(
                        "Festa privata",
                        "Feste private per occasioni speciali: anniversari, lauree, ricorrenze"
                );
                tipologiaEventoRepository.save(festaPrivata);
                System.out.println("✓ Salvata tipologia: " + festaPrivata.getNome());

                // Tipologia senza descrizione (facoltativa)
                TipologiaEvento altro = new TipologiaEvento("Altro");
                tipologiaEventoRepository.save(altro);
                System.out.println("✓ Salvata tipologia: " + altro.getNome() + " (senza descrizione)");

                System.out.println("========================================");
                System.out.println("✓ DATI CARICATI CON SUCCESSO!");
                System.out.println("Totale tipologie inserite: " + tipologiaEventoRepository.count());
                System.out.println("========================================");

                // Stampa tutte le tipologie per verifica
                System.out.println("\nELENCO TIPOLOGIE NEL DATABASE:");
                tipologiaEventoRepository.findAll().forEach(t ->
                        System.out.println("  - ID: " + t.getId() + " | Nome: " + t.getNome())
                );
                System.out.println("========================================\n");

            } else {
                System.out.println("========================================");
                System.out.println("DATABASE TIPOLOGIA_EVENTO GIÀ POPOLATO");
                System.out.println("Trovate " + count + " tipologie esistenti.");
                System.out.println("========================================\n");
            }
        };
    }
}
