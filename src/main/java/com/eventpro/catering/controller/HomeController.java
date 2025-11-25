package com.eventpro.catering.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @RestController indica che la classe espone endpoint REST, unendo @Controller e @ResponseBody;
 * è necessario per comunicare a Spring che qui deve gestire richieste HTTP restituendo direttamente i dati.
 */
@RestController
public class HomeController {

    /**
     * @GetMapping associa questo metodo alle richieste HTTP GET sull'URL "/";
     * definisce quindi un endpoint, cioè un punto d'accesso all'API raggiungibile dal client.
     */
    @GetMapping("/")
    public String home() {
        return "Catering API attiva";
    }
}
