package com.eventpro.catering;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * CLASSE PRINCIPALE DELL'APPLICAZIONE SPRING BOOT
 *
 * Questa è il punto di ingresso dell'intera applicazione EventPro Catering.
 * Quando esegui "mvn spring-boot:run" o avvii il JAR, Java cerca questa classe
 * con il metodo main() e la esegue.
 *
 * COSA FA QUESTA CLASSE:
 * 1. Contiene il metodo main() che avvia l'applicazione
 * 2. Ha l'annotazione @SpringBootApplication che configura automaticamente Spring
 * 3. Inizializza il server Tomcat che ascolta le richieste HTTP sulla porta 8080
 */
@SpringBootApplication
public class EventProCateringApplication {

    /**
     * METODO MAIN - PUNTO DI INGRESSO DELL'APPLICAZIONE
     *
     * COSA SUCCEDE QUANDO ESEGUI QUESTO METODO:
     * 1. SpringApplication.run() inizializza il contesto Spring
     * 2. Scansiona il package "com.eventpro.catering" e tutti i sottopacchetti
     * 3. Trova automaticamente tutte le classi annotate con @Component, @Service, @Controller, ecc.
     * 4. Crea le istanze dei bean (oggetti gestiti da Spring)
     * 5. Avvia il server web Tomcat che ascolta sulla porta 8080
     * 6. L'applicazione rimane in esecuzione in attesa di richieste HTTP
     *
     * PARAMETRI:
     * - EventProCateringApplication.class: dice a Spring quale classe è il punto di ingresso
     * - args: argomenti da linea di comando (opzionali)
     *
     * QUANDO USARE QUESTO:
     * - Lo esegui una sola volta all'avvio dell'applicazione
     * - Non lo chiami mai manualmente nel codice
     *
     * @param args argomenti da linea di comando (ad esempio: --server.port=9090)
     */
    public static void main(String[] args) {
        SpringApplication.run(EventProCateringApplication.class, args);
    }

}