package fr.formation.spring.bibliotech.api.dto;


import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;

@Data
public class ErrorDto {
    private LocalDateTime timestamp;
    private int status;
    private String error;
    private String message;
    private String path;
    // Utilisé pour les erreurs de validation
    private Map<String, String> validationErrors;

    public ErrorDto(int status, String error, String message, String path) {
        this.timestamp = LocalDateTime.now();
        this.status = status;
        this.error = error;
        this.message = message;
        this.path = path;
    }
}


