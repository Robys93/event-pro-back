## EventPro - Catering API (Spring Boot)

Progetto didattico Spring Boot 3 pensato per un gestionale di catering. L'obiettivo immediato è avere un backend che si avvia senza errori e mostra **"Catering API attiva"** su `http://localhost:8080/`.

### 1. Creazione progetto con Spring Initializr
1. Apri [start.spring.io](https://start.spring.io/)
2. Parametri suggeriti:
   - **Project:** Maven
   - **Language:** Java
   - **Spring Boot:** 3.3.x
   - **Group:** `com.eventpro`
   - **Artifact:** `catering`
   - **Java:** 17
3. Dipendenze da selezionare:
   - Spring Web
   - Spring Data JPA
   - Spring Security (solo dipendenza)
   - H2 Database
   - Validation
   - Lombok

Scarica lo zip e sovrascrivi con i file presenti in questo repository (o viceversa) per mantenere la stessa struttura.

### 2. Importazione in IntelliJ IDEA
1. Apri IntelliJ → **File → Open...**
2. Seleziona la cartella del progetto `event-pro-back`
3. IntelliJ riconoscerà automaticamente il progetto Maven e scaricherà le dipendenze
4. Accetta l'abilitazione di Lombok (Settings → Build Tools → Annotation Processors → Enable)

### 3. Avvio dell'applicazione
```bash
mvn spring-boot:run
```
Oppure esegui la classe `CateringApplication` da IntelliJ. Nel log dovresti leggere:
```
Started CateringApplication in X seconds
Tomcat started on port 8080
```

### 4. Configurazione importante (`application.properties`)
- H2 in memory (`jdbc:h2:mem:eventprodb`)
- `spring.jpa.show-sql=true` per visualizzare le query
- Console H2 disponibile su `http://localhost:8080/h2-console`

### 5. Endpoint disponibili (fase 1)
| Metodo | Path | Descrizione | Esempio curl |
|--------|------|-------------|--------------|
| GET | `/` | Smoke test | `curl http://localhost:8080/`
| GET | `/api/health` | Stato applicazione | `curl http://localhost:8080/api/health`
| POST | `/api/auth/register` | Struttura registrazione | `curl -X POST http://localhost:8080/api/auth/register -H "Content-Type: application/json" -d '{"firstName":"Anna","lastName":"Bianchi","email":"anna@example.com","password":"password123","companyName":"EventPro","inviteCode":"ABC123"}'`
| POST | `/api/auth/login` | Struttura login | `curl -X POST http://localhost:8080/api/auth/login -H "Content-Type: application/json" -d '{"email":"anna@example.com","password":"password123"}'`

### 6. Modello dati attuale
- **User ↔ Company**: relazione `@ManyToOne` (molti utenti appartengono a una company).
- **Event ↔ Client**: relazione `@ManyToOne` (ogni evento ha un cliente di riferimento).
- Annotazioni JPA già pronte (`@Entity`, `@Table`, `@ManyToOne`, `@OneToMany`).

### 7. Sicurezza e validazione
- Spring Security è presente solo come dipendenza, da configurare nelle prossime iterazioni.
- Validazione input tramite `jakarta.validation` nelle DTO (`@Email`, `@NotBlank`, `@Size`).

### 8. Prossimi passi consigliati
1. Implementare hashing password (es. `BCryptPasswordEncoder`).
2. Configurare Spring Security con JWT o sessioni.
3. Aggiungere entity `Company` al livello REST per gestire inviti univoci.
4. Introdurre servizi/endpoint CRUD completi per Client ed Event.
5. Aggiungere test automatici (unit/integration) per coprire controller e service.

### 9. Risorse utili
- [Guida Spring Boot ufficiale](https://spring.io/guides/gs/spring-boot)
- [Best practice REST](https://stackoverflow.blog/2020/03/02/best-practices-for-rest-api-design/)
- [Video tutorial Spring Boot](https://www.youtube.com/watch?v=gJrjgg1KVL4)

Buon sviluppo con EventPro! :rocket:
