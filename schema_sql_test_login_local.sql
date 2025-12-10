DROP SCHEMA IF EXISTS catering;
CREATE SCHEMA IF NOT EXISTS catering;
USE catering;

-- ============================================================================
-- TABELLA CLIENTE (STUB)
-- ============================================================================
-- Tabella stub per permettere la relazione con Evento

CREATE TABLE IF NOT EXISTS cliente (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100),
    cognome VARCHAR(100),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- ============================================================================
-- TABELLA TIPOLOGIA_EVENTO
-- ============================================================================
-- Questa tabella classifica i tipi di eventi gestiti nel sistema

CREATE TABLE IF NOT EXISTS tipologia_evento (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL UNIQUE,
    descrizione VARCHAR(500),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- ============================================================================
-- TABELLA EVENTO
-- ============================================================================
-- Questa tabella contiene gli eventi del sistema catering
-- Ogni evento è collegato a una tipologia e opzionalmente a un cliente

CREATE TABLE IF NOT EXISTS evento (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(200) NOT NULL,
    data DATE NOT NULL,
    ora_inizio TIME NOT NULL,
    ora_fine TIME NOT NULL,
    location VARCHAR(300) NOT NULL,
    note TEXT,
    evento_giornaliero BOOLEAN NOT NULL DEFAULT FALSE,

    -- RELAZIONE CON TIPOLOGIA_EVENTO (OBBLIGATORIA)
    tipologia_evento_id BIGINT NOT NULL,

    -- RELAZIONE CON CLIENTE (OPZIONALE)
    cliente_id BIGINT,

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    -- FOREIGN KEYS
    FOREIGN KEY (tipologia_evento_id) REFERENCES tipologia_evento(id) ON DELETE RESTRICT,
    FOREIGN KEY (cliente_id) REFERENCES cliente(id) ON DELETE SET NULL
);

-- ============================================================================
-- TABELLA UTENTE
-- ============================================================================
-- Questa tabella contiene gli utenti del sistema catering
-- Stessa struttura della Entity Utente in Spring Boot

CREATE TABLE utente (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(50) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- ============================================================================
-- INSERIMENTO DATI DI TEST
-- ============================================================================
-- Inseriamo i 3 utenti di test che abbiamo usato in H2 con il campo ROLE

INSERT INTO utente (email, password, role) VALUES ('test@example.com', 'test', 'USER');
INSERT INTO utente (email, password, role) VALUES ('daniele@example.com', 'daniele', 'USER');
INSERT INTO utente (email, password, role) VALUES ('admin@example.com', 'admin', 'ADMIN');

-- ============================================================================
-- VERIFICA DATI INSERITI
-- ============================================================================
SHOW TABLES;

SELECT * FROM cliente;
SELECT * FROM tipologia_evento;
SELECT * FROM evento;
SELECT * FROM utente;