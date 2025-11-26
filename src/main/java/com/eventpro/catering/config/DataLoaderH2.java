package com.eventpro. catering.config;

import com.eventpro.catering.model.Utente;
import com.eventpro.catering.repository.UtenteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * CONFIGURAZIONE PER CARICAMENTO DATI H2
 *
 * Questa classe carica automaticamente i dati di test nel database H2
 * quando l'applicazione si avvia in modalità H2.
 *
 * COSA FA:
 * 1. Crea 3 utenti di test predefiniti
 * 2. Li salva nel database H2
 * 3. Stampa un messaggio di conferma nel log
 *
 * PERCHÉ SERVE:
 * - In sviluppo non vogliamo creare manualmente i dati ogni volta
 * - Vogliamo che il database abbia sempre gli stessi dati di test
 * - Quando riavvii l'app, i dati vengono ricreati (H2 in memoria)
 *
 * ANNOTAZIONI USATE:
 * - @Configuration: dice a Spring che questa è una classe di configurazione
 * - @Profile("h2"): questa classe viene caricata SOLO quando il profilo attivo è "h2"
 *   Se il profilo è "dev" (MySQL), questa classe viene ignorata
 */
@Configuration
@Profile("h2")
public class DataLoaderH2 {

    /**
     * LOGGER PER STAMPARE MESSAGGI NELLA CONSOLE/LOG
     *
     * Un logger è uno strumento per stampare messaggi durante l'esecuzione
     * Usi LoggerFactory. getLogger(DataLoaderH2.class) per ottenere un logger
     * specifico per questa classe
     *
     * LIVELLI DI LOG (dal meno al più grave):
     * - debug(): per messaggi di debug (informazioni dettagliate)
     * - info(): per informazioni generali
     * - warn(): per avvertimenti (qualcosa potrebbe essere sbagliato)
     * - error(): per errori (qualcosa è andato male)
     */
    private static final Logger logger = LoggerFactory.getLogger(DataLoaderH2.class);

    /**
     * METODO CHE CARICA I DATI NEL DATABASE H2
     *
     * @Bean: dice a Spring di creare un bean (oggetto gestito da Spring)
     * CommandLineRunner: un'interfaccia che Spring esegue all'avvio dell'applicazione
     *
     * COME FUNZIONA:
     * 1. Spring vede che c'è un @Bean di tipo CommandLineRunner
     * 2. Crea una lambda function che implementa CommandLineRunner
     * 3. All'avvio dell'applicazione, esegue il metodo run() di questa lambda
     * 4. Nel metodo run(), carica i 3 utenti nel database
     *
     * PARAMETRO:
     * - utenteRepository: Spring lo inietta automaticamente
     *   (il Repository che abbiamo creato prima)
     *
     * @param utenteRepository il repository per salvare gli utenti nel database
     * @return un CommandLineRunner che esegue il codice all'avvio
     */
    @Bean
    public CommandLineRunner loadData(UtenteRepository utenteRepository) {

        // Restituiamo una lambda function (un CommandLineRunner)
        // La lambda verrà eseguita all'avvio dell'applicazione
        return args -> {

            // Stampiamo un messaggio di inizio nel log
            logger.info("=". repeat(70));
            logger.info("CARICAMENTO DATI DI TEST NEL DATABASE H2");
            logger.info("=".repeat(70));

            // Creiamo il primo utente: test / test
            Utente utente1 = new Utente("test", "test");

            // Creiamo il secondo utente: daniele / daniele
            Utente utente2 = new Utente("daniele", "daniele");

            // Creiamo il terzo utente: admin / admin
            Utente utente3 = new Utente("admin", "admin");

            // Salviamo tutti gli utenti nel database H2
            // utenteRepository.save() esegue una query INSERT nel database
            utenteRepository.save(utente1);
            utenteRepository.save(utente2);
            utenteRepository.save(utente3);

            // Stampiamo un messaggio di conferma che tutto è andato bene
            logger.info("✓ Utente 1 creato: test / test");
            logger.info("✓ Utente 2 creato: daniele / daniele");
            logger.info("✓ Utente 3 creato: admin / admin");

            // Contiamo quanti utenti sono nel database e lo stampiamo
            long totalUtenti = utenteRepository.count();
            logger.info("✓ Totale utenti nel database: {}", totalUtenti);

            // Stampiamo il messaggio di conferma della connessione al database "Catering"
            logger.info("Connected to database: Catering");

            logger.info("✓ Caricamento dati completato con successo!");
            logger.info("=".repeat(70));
            logger.info("=".repeat(70));
            logger.info("✓ CONNESSIONE AL DATABASE 'CATERING' CONFERMATA");
            logger.info("=". repeat(70));
        };
    }

}