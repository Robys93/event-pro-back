package com.eventpro.catering.dto;

public class AuthResponse {

    private String message;
    private String accessToken;  // Per ora sarà null, lo useremo con JWT nella Task C

    // Costruttore vuoto
    public AuthResponse() {
    }

    // Costruttore con solo messaggio (per Task B)
    public AuthResponse(String message) {
        this.message = message;
        this.accessToken = null;
    }

    // Costruttore completo (per Task C con JWT)
    public AuthResponse(String message, String accessToken) {
        this.message = message;
        this.accessToken = accessToken;
    }

    // Getter e Setter
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
