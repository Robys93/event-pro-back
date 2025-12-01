# event-pro-back
Task 1 -> Creazione progetto Spring Boot.<br>
Quando avviato, in localhost:8080 appare risposta di string "Catering API attiva".<br>
Task 2-> Collegare il progetto a un database.<br>
Log di avvio contiene conferma di connessione al database "Catering".
Task 7-> Login e registrazione A.<br>
```
Crea entità user
Obiettivo: avere qualcosa da salvare a DB.
Cose da fare:
Creare entity User con:
Long id
String email (unique, not null)
String password
String role (es. "USER")
Annotare con @Entity e @Table("users")
Creare UserRepository che estende JpaRepository<User, Long>
metodo Optional<User> findByEmail(String email)
metodo boolean existsByEmail(String email)
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
