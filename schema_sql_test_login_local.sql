DROP SCHEMA IF EXISTS catering;
CREATE SCHEMA IF NOT EXISTS catering;
USE catering;

-- ============================================================================
-- TABELLA UTENTE
-- ============================================================================
-- Questa tabella contiene gli utenti del sistema catering
-- Stessa struttura della Entity Utente in Spring Boot

CREATE TABLE utente (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome_utente VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- ============================================================================
-- INSERIMENTO DATI DI TEST
-- ============================================================================
-- Inseriamo i 3 utenti di test che abbiamo usato in H2

INSERT INTO utente (nome_utente, password) VALUES ('test', 'test');
INSERT INTO utente (nome_utente, password) VALUES ('daniele', 'daniele');
INSERT INTO utente (nome_utente, password) VALUES ('admin', 'admin');

-- ============================================================================
-- VERIFICA DATI INSERITI
-- ============================================================================

SELECT * FROM utente;