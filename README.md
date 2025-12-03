# event-pro-back
Task 1 -> Creazione progetto Spring Boot.<br>
Quando avviato, in localhost:8080 appare risposta di string "Catering API attiva".<br>
Task 2-> Collegare il progetto a un database.<br>
Log di avvio contiene conferma di connessione al database "Catering".<br>
Task 7A-> Login e registrazione A.<br>
Aggiornamento Utente, SQL con email invece di nome_utente, creazione metodi nel Repository.<br>
Task 7B-> Login e registrazione B.<br>
Autenticazione con Spring Security, registrazione sicura e login. Endpoint pubblici /api/auth/**.
Tsk 7C-> Login e registrazione C.<br>
```
Creare JwtService molto minimale:
-generateToken(String username) → crea un token con una secret fissa e una scadenza.
-extractUsername(String token).
Modificare login:
-se l’autenticazione va a buon fine, invece di “login ok” restituisce un JSON con dentro il token (es. AuthResponse { accessToken }).
Creare JwtAuthenticationFilter che:
-legge l’header Authorization: Bearer <token>
-usa JwtService per estrarre lo username
-carica l’utente da CustomUserDetailsService
-se è tutto valido, setta l’utente nel SecurityContext.
Aggiornare SecurityConfig:
-registrare il filtro: .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
Creare un endpoint di prova, es:
-GET /api/user/me
-restituisce l’email dell’utente loggato
-NON deve stare sotto /api/auth/** (quindi è protetto).
Verifica finale:
-chiamata a /api/auth/login → ricevo token
-chiamata a /api/user/me con header Authorization: Bearer <token> → devo ricevere i dati dell’utente.
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
