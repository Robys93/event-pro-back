# event-pro-back
Task 1 -> Creazione progetto Spring Boot.<br>
Quando avviato, in localhost:8080 appare risposta di string "Catering API attiva".<br>
Task 2-> Collegare il progetto a un database.<br>
Log di avvio contiene conferma di connessione al database "Catering".<br>
Task 7A-> Login e registrazione A.<br>
Aggiornamento Utente, SQL con email invece di nome_utente, creazione metodi nel Repository.<br>
Task 7B-> Login e registrazione B.<br>
Autenticazione con Spring Security, registrazione sicura e login. Endpoint pubblici /api/auth/**.<br>
Tsk 7C-> Login e registrazione C.<br>
Implementazione JwtService per gestione token e autorizzazioni. Endpoint di login /api/auth/login, endpoint di user /api/user/me.<br>
Task 5-> Creare i modelli Evento e TipologiaEvento.<br>
Creazione Model Evento e gestione dei vari eventi nel DB.<br>
Task 6-> Creare le API per aggiungere e leggere gli Eventi.<br>
Aggiunta diversi endpoint di chiamata per eventi.

Per la connessione al localhost di MySQL ricordarsi che l'utenza e password base erano root entrambe, se avete cambiato una o entrambe dovete aggiornare i nomi nel file:<br>
```application-dev.properties```<br>
Specificatamente in riga 41 e 45<br>
```
spring.datasource.username
spring.datasource.password
```
Con i vostri username e password del localhost di MySQL.