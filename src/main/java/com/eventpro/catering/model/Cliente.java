package com.eventpro.catering. model;

import jakarta.persistence.*;

/**
 * ENTITY CLIENTE - STUB MINIMALE
 *
 * Questa è una versione minimale dell'entità Cliente.
 * Serve solo per permettere la relazione con Evento.
 *
 * IMPORTANTE:
 * - Questa classe verrà espansa con tutti i campi richiesti
 * - Per ora contiene solo i campi base necessari per far compilare il codice
 */
@Entity
@Table(name = "cliente")
public class Cliente {

    /**
     * ID UNIVOCO DEL CLIENTE
     */
    @Id
    @GeneratedValue(strategy = GenerationType. IDENTITY)
    private Long id;

    /**
     * NOME DEL CLIENTE (campo base per ora)
     */
    @Column(length = 100)
    private String nome;

    /**
     * COGNOME DEL CLIENTE (campo base per ora)
     */
    @Column(length = 100)
    private String cognome;

    // ========================================================================
    // COSTRUTTORI
    // ========================================================================

    /**
     * Costruttore vuoto (richiesto da Hibernate)
     */
    public Cliente() {
    }

    /**
     * Costruttore con nome e cognome
     */
    public Cliente(String nome, String cognome) {
        this.nome = nome;
        this.cognome = cognome;
    }

    // ========================================================================
    // GETTER E SETTER
    // ========================================================================

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    // ========================================================================
    // METODO toString (UTILE PER DEBUG)
    // ========================================================================

    @Override
    public String toString() {
        return "Cliente{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", cognome='" + cognome + '\'' +
                '}';
    }
}