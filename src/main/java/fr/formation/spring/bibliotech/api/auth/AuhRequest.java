package fr.formation.spring.bibliotech.api.auth;

import lombok.Data;

@Data
public class AuhRequest {
    private String username;
    private String password;
}
