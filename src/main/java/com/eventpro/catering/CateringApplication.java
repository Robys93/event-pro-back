package com.eventpro.catering;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @SpringBootApplication abilita l'auto-configurazione di Spring Boot e la scansione dei bean; è necessaria
 * per dire al framework dove iniziare a cercare componenti e configurazioni, così l'app si avvia correttamente.
 */
@SpringBootApplication
public class CateringApplication {

    /**
     * Il metodo main avvia l'applicazione Spring Boot usando SpringApplication.run, che prepara il contesto e
     * accende il server embedded.
     */
    public static void main(String[] args) {
        SpringApplication.run(CateringApplication.class, args);
    }
}
