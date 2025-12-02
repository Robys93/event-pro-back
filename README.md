# event-pro-back
Task 1 -> Creazione progetto Spring Boot.<br>
Quando avviato, in localhost:8080 appare risposta di string "Catering API attiva".<br>
Task 2-> Collegare il progetto a un database.<br>
Log di avvio contiene conferma di connessione al database "Catering".<br>
Task 7A-> Login e registrazione A.<br>
Aggiornamento Utente, SQL con email invece di nome_utente, creazione metodi nel Repository.<br>
Task 7B-> Login e registrazione B.<br>
```
Aggiungere dipendenza spring-boot-starter-security.
Creare CustomUserDetailsService che:
-usa UserRepository.findByEmail(...)
-costruisce un UserDetails con email + password.

Creare SecurityConfig very basic:
-espone PasswordEncoder (BCrypt)
-espone AuthenticationManager
-in SecurityFilterChain:
--permettere /api/auth/**
--tutto il resto autenticato
--csrf disabilitata, SESSION_STATELESS

Modificare registrazione per:
-criptare la password con passwordEncoder.encode(...).

Creare LoginRequest (DTO) con email, password.

In AuthController aggiungere:
-POST /api/auth/login
-usa AuthenticationManager.authenticate(...)
-se le credenziali sono giuste, risponde semplicemente 200 OK con un messaggio tipo “login ok”.
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
