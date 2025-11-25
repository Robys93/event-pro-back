package com.eventpro.catering;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * CONTROLLER REST PER LE OPERAZIONI DI BASE
 *
 * Un controller è una classe che "controlla" le richieste HTTP in arrivo.
 * Quando il client (browser, Postman, app mobile) fa una richiesta HTTP,
 * Spring la instrada verso il metodo appropriato in questo controller.
 * -
 * @RestController è un'annotazione che combina:
 * - @Controller: marca questa classe come controller MVC
 * - @ResponseBody: ogni metodo ritorna automaticamente JSON, non una view HTML
 *
 * PERCHÉ SERVE @RestController:
 * - Spring riconosce questa classe e la registra come controller
 * - Ogni metodo annotato con @GetMapping, @PostMapping, ecc. diventa un "endpoint API"
 * - Le risposte vengono automaticamente convertite in JSON
 *
 * COME FUNZIONA:
 * 1. Il client invia una richiesta HTTP GET a http://localhost:8080/
 * 2. Spring riceve la richiesta e cerca un metodo con @GetMapping("/")
 * 3. Trova il metodo welcome() in questa classe
 * 4. Esegue il metodo e riceve la stringa "Catering API attiva"
 * 5. Converte il risultato in JSON (in questo caso è solo testo)
 * 6. Invia la risposta HTTP con status 200 (OK) al client
 */
@RestController
public class HomeController {

    /**
     * ENDPOINT DI TEST - VERIFICA CHE L'API SIA ATTIVA
     *
     * @GetMapping("/")
     * - Mappa una richiesta HTTP GET all'URL "/"
     * - "/" è la radice del dominio, quindi: http://localhost:8080/
     *
     * PERCHÉ SERVE:
     * - È il primo endpoint che crei per verificare che tutto funzioni
     * - Se accedi a http://localhost:8080/ e vedi "Catering API attiva",
     *   significa che l'applicazione è in esecuzione correttamente
     *
     * COME TESTARLO:
     * 1. Esegui l'applicazione: mvn spring-boot:run
     * 2. Apri il browser: http://localhost:8080/
     * 3. Dovresti vedere la risposta: "Catering API attiva"
     *
     * ALTERNATIVA CON POSTMAN:
     * 1. Apri Postman
     * 2. Crea una richiesta GET a http://localhost:8080/
     * 3. Clicca "Send"
     * 4. Nella sezione "Response", vedrai la stringa ritornata
     *
     * @return stringa "Catering API attiva" che viene inviata al client
     */
    @GetMapping("/")
    public String welcome() {
        return "Catering API attiva";
    }

}