package com.eventpro.catering.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * CONFIGURAZIONE PER CARICAMENTO DATI MYSQL
 *
 * Questa classe carica automaticamente i dati nel database MySQL
 * quando l'applicazione si avvia in modalità dev (MySQL).
 *
 * COSA FA:
 * 1. Verifica la connessione al database MySQL
 * 2. Stampa un messaggio di conferma nel log
 *
 * PERCHÉ SERVE:
 * - Confermare che la connessione a MySQL è avvenuta correttamente
 * - Mostrare il nome del database a cui ci siamo collegati
 *
 * ANNOTAZIONI USATE:
 * - @Configuration: dice a Spring che questa è una classe di configurazione
 * - @Profile("dev"): questa classe viene caricata SOLO quando il profilo attivo è "dev"
 */
@Configuration
@Profile("dev")
public class DataLoaderMySQL {

    /**
     * LOGGER PER STAMPARE MESSAGGI NELLA CONSOLE/LOG
     */
    private static final Logger logger = LoggerFactory.getLogger(DataLoaderMySQL.class);

    /**
     * METODO CHE VERIFICA LA CONNESSIONE AL DATABASE MYSQL
     *
     * @Bean: dice a Spring di creare un bean (oggetto gestito da Spring)
     * CommandLineRunner: un'interfaccia che Spring esegue all'avvio dell'applicazione
     *
     * PARAMETRO:
     * - dataSource: Spring lo inietta automaticamente
     *   (la sorgente dati configurata in application-dev.properties)
     *
     * @param dataSource la sorgente dati MySQL
     * @return un CommandLineRunner che esegue il codice all'avvio
     */
    @Bean
    public CommandLineRunner verifyMySQLConnection(DataSource dataSource) {

        return args -> {

            try {
                // Otteniamo una connessione dal DataSource
                Connection connection = dataSource.getConnection();

                // Stampiamo i dettagli della connessione
                logger.info("=".repeat(70));
                logger.info("✓ CONNESSIONE AL DATABASE 'CATERING' CONFERMATA");
                logger.info("✓ Database URL: {}", connection.getMetaData().getURL());
                logger.info("✓ Database Utente: {}", connection.getMetaData().getUserName());
                logger.info("=".repeat(70));

                // Stampiamo i dati degli utenti nel database
                logger.info("======================================================================");
                logger.info("UTENTI NEL DATABASE:");
                logger.info("======================================================================");

                try (Statement stmt = connection.createStatement()) {
                    ResultSet rs = stmt.executeQuery("SELECT * FROM utente");
                    while (rs.next()) {
                        logger.info("✓ ID: {} | Email: {} | Password: {}",
                                rs.getLong("id"),
                                rs.getString("email"),
                                rs.getString("password"));
                    }
                }

                logger.info("======================================================================");

                // Chiudiamo la connessione
                connection.close();

            } catch (Exception e) {
                logger.error("✗ Errore nella connessione al database: {}", e.getMessage());
            }
        };
    }
}