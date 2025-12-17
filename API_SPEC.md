# 📋 API Specification - Event Pro Backend

Documentazione completa degli endpoint per l'integrazione Frontend-Backend.

**Base URL (development):** `http://localhost:8080`

---

## 🔐 Autenticazione

### 1. Registrazione Utente

**Endpoint:** `POST /api/auth/register`

**Descrizione:** Registra un nuovo utente nel sistema.

**Request Body:**
```json
{
  "nome": "Mario",
  "cognome": "Rossi",
  "email": "mario.rossi@example.com",
  "password": "password123",
  "role": "USER"
}
```

**Response Success (201 Created):**
```json
{
  "message": "Registrazione completata con successo",
  "accessToken": null
}
```

**Response Error (400 Bad Request):**
```json
{
  "message": "Email già registrata",
  "accessToken": null
}
```

---

### 2. Login Utente

**Endpoint:** `POST /api/auth/login`

**Descrizione:** Effettua il login e restituisce un JWT token.

**Request Body:**
```json
{
  "email": "mario.rossi@example.com",
  "password": "password123"
}
```

**Response Success (200 OK):**
```json
{
  "message": "Login effettuato con successo",
  "accessToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}
```

**Response Error (401 Unauthorized):**
```json
{
  "message": "Email o password non corretti",
  "accessToken": null
}
```

**Note:**
- Il token JWT deve essere incluso in tutte le richieste protette nell'header:  
  `Authorization: Bearer <accessToken>`
- Il token ha una validità di 24 ore

---

## 👤 Utente Autenticato

### 3. Ottieni Profilo Utente

**Endpoint:** `GET /api/user/me`

**Descrizione:** Restituisce le informazioni dell'utente attualmente autenticato.

**Headers Required:**
```
Authorization: Bearer <accessToken>
```

**Response Success (200 OK):**
```json
{
  "email": "mario.rossi@example.com",
  "message": "Utente autenticato con successo"
}
```

**Response Error (401 Unauthorized):**
```json
"Utente non autenticato"
```

---

## 🎉 Eventi

### 4. Lista Tutti gli Eventi

**Endpoint:** `GET /api/eventi`

**Descrizione:** Restituisce la lista di tutti gli eventi.

**Headers Required:**
```
Authorization: Bearer <accessToken>
```

**Response Success (200 OK):**
```json
[
  {
    "id": 1,
    "nomeCliente": "Mario Rossi",
    "telefono": "+39 123 456 7890",
    "numeroPersone": 50,
    "data": "2024-06-15",
    "ora": "18:00:00",
    "luogo": "Villa Esempio",
    "tipologiaEvento": {
      "id": 1,
      "nome": "Matrimonio",
      "descrizione": "Evento matrimoniale"
    },
    "note": "Preferenza menu vegetariano"
  },
  {
    "id": 2,
    "nomeCliente": "Laura Bianchi",
    "telefono": "+39 098 765 4321",
    "numeroPersone": 30,
    "data": "2024-07-20",
    "ora": "20:00:00",
    "luogo": "Ristorante Centrale",
    "tipologiaEvento": {
      "id": 2,
      "nome": "Compleanno",
      "descrizione": "Festa di compleanno"
    },
    "note": null
  }
]
```

---

### 5. Lista Eventi per Data

**Endpoint:** `GET /api/eventi?data=2024-06-15`

**Descrizione:** Restituisce solo gli eventi di una specifica data.

**Query Parameters:**
- `data` (required): Data nel formato ISO 8601 (`YYYY-MM-DD`)

**Headers Required:**
```
Authorization: Bearer <accessToken>
```

**Response Success (200 OK):**
```json
[
  {
    "id": 1,
    "nomeCliente": "Mario Rossi",
    "telefono": "+39 123 456 7890",
    "numeroPersone": 50,
    "data": "2024-06-15",
    "ora": "18:00:00",
    "luogo": "Villa Esempio",
    "tipologiaEvento": {
      "id": 1,
      "nome": "Matrimonio",
      "descrizione": "Evento matrimoniale"
    },
    "note": "Preferenza menu vegetariano"
  }
]
```

**Response Error (400 Bad Request):**
```json
"Formato data non valido"
```

---

### 6. Dettaglio Evento per ID

**Endpoint:** `GET /api/eventi/{id}`

**Descrizione:** Restituisce i dettagli di un singolo evento.

**Path Parameters:**
- `id` (required): ID numerico dell'evento

**Headers Required:**
```
Authorization: Bearer <accessToken>
```

**Response Success (200 OK):**
```json
{
  "id": 1,
  "nomeCliente": "Mario Rossi",
  "telefono": "+39 123 456 7890",
  "numeroPersone": 50,
  "data": "2024-06-15",
  "ora": "18:00:00",
  "luogo": "Villa Esempio",
  "tipologiaEvento": {
    "id": 1,
    "nome": "Matrimonio",
    "descrizione": "Evento matrimoniale"
  },
  "note": "Preferenza menu vegetariano"
}
```

**Response Error (404 Not Found):**
```json
"Evento con ID 999 non trovato"
```

---

### 7. Crea Nuovo Evento

**Endpoint:** `POST /api/eventi`

**Descrizione:** Crea un nuovo evento nel sistema.

**Headers Required:**
```
Authorization: Bearer <accessToken>
Content-Type: application/json
```

**Request Body:**
```json
{
  "nomeCliente": "Giovanni Verdi",
  "telefono": "+39 111 222 3333",
  "numeroPersone": 80,
  "data": "2024-08-10",
  "ora": "19:30:00",
  "luogo": "Hotel Panorama",
  "tipologiaEventoId": 1,
  "note": "Richiesta menu senza glutine"
}
```

**Response Success (201 Created):**
```json
{
  "id": 3,
  "nomeCliente": "Giovanni Verdi",
  "telefono": "+39 111 222 3333",
  "numeroPersone": 80,
  "data": "2024-08-10",
  "ora": "19:30:00",
  "luogo": "Hotel Panorama",
  "tipologiaEvento": {
    "id": 1,
    "nome": "Matrimonio",
    "descrizione": "Evento matrimoniale"
  },
  "note": "Richiesta menu senza glutine"
}
```

**Response Error (400 Bad Request):**
```json
"Dati non validi o incompleti"
```

---

## 🏠 Home (Pubblico)

### 8. Home Page

**Endpoint:** `GET /api/home`

**Descrizione:** Endpoint pubblico di benvenuto.

**Response Success (200 OK):**
```json
{
  "message": "Benvenuto nella home page pubblica!",
  "isAuthenticated": false
}
```

---

## 📝 Note per il Frontend

### Gestione Token JWT

1. **Dopo il login:** Salvare il `accessToken` in `localStorage` o `sessionStorage`
2. **Per ogni richiesta protetta:** Includere l'header:
   ```javascript
   headers: {
     'Authorization': `Bearer ${accessToken}`,
     'Content-Type': 'application/json'
   }
   ```
3. **Gestione errori 401:** Se ricevi 401, il token è scaduto → reindirizzare al login

### Formati Data/Ora

- **Date:** Formato ISO 8601 `YYYY-MM-DD` (es: `2024-06-15`)
- **Time:** Formato `HH:mm:ss` (es: `18:00:00`)

### CORS

Il backend è configurato per accettare richieste da:
- `http://localhost:5173` (Vite dev server)
- `http://localhost:3000` (React/Next.js dev)

### Status Code Reference

| Status Code | Significato |
|-------------|-------------|
| 200 | OK - Richiesta completata con successo |
| 201 | Created - Risorsa creata con successo |
| 400 | Bad Request - Dati non validi |
| 401 | Unauthorized - Token mancante o non valido |
| 403 | Forbidden - Permessi insufficienti |
| 404 | Not Found - Risorsa non trovata |
| 500 | Internal Server Error - Errore server |

---

## 🧪 Testing con Postman/Insomnia

### Flusso di Test Completo

1. **Registrazione:**
   ```
   POST http://localhost:8080/api/auth/register
   Body: { email, password, nome, cognome, role }
   ```

2. **Login:**
   ```
   POST http://localhost:8080/api/auth/login
   Body: { email, password }
   → Copia il token dalla response
   ```

3. **Test Token:**
   ```
   GET http://localhost:8080/api/user/me
   Header: Authorization: Bearer <token>
   ```

4. **Lista Eventi:**
   ```
   GET http://localhost:8080/api/eventi
   Header: Authorization: Bearer <token>
   ```

5. **Crea Evento:**
   ```
   POST http://localhost:8080/api/eventi
   Header: Authorization: Bearer <token>
   Body: { nomeCliente, telefono, numeroPersone, data, ora, luogo, tipologiaEventoId, note }
   ```

---

**Ultimo aggiornamento:** 17 Dicembre 2025  
**Versione Backend:** 1.0.0  
**Contatto:** Per domande contattare il team backend
