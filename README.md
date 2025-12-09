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
```
Crea due classi nel backend:
-TipologiaEvento: id, nome, descrizione (facoltativa)
-Evento: nome, data, oraInizio, oraFine, location, note (facoltativa), flag eventoGiornaliero, collegamento al Cliente (può essere vuoto) e alla TipologiaEvento

Completato quando
-Le tabelle Evento e TipologiaEvento sono presenti nel database
-Puoi creare un Evento da codice e salvarlo nel DB
```
Nella repository è presente il file di nome<br>
```schema_sql_test_login_local.sql```<br>
Nel file è presente una struttura query pronta all'uso per creare e popolare lo schema richiesto. Apritelo nel workbench di MySQL e fate partire lo script.<br>
Se il vostro server di MySQL risulta "Stopped" nel server status del workbench, ricordarsi di controllare nei servizi di windows se il servizio di MySQL è stato arrestato.<br>
In quel caso, avviatelo e dovrebbe essere pronto all'uso nel workbench.

Per la connessione al localhost di MySQL ricordarsi che l'utenza e password base erano root entrambe, se avete cambiato una o entrambe dovete aggiornare i nomi nel file:<br>
```application-dev.properties```<br>
Specificatamente in riga 41 e 45<br>
```
spring.datasource.username
spring.datasource.password
```
Con i vostri username e password del localhost di MySQL.

Se ci sono ancora problemi con MySQL e volete testare in locale utilizzando H2, basta andare in<br>
```application.properties```<br>
In riga 27 cambiare il comando da "dev" a "H2"<br>
```
spring.profiles.active
```
<br>
Questo vi permetterà di utilizzare lo schema creato in RAM da SpringBoot.
