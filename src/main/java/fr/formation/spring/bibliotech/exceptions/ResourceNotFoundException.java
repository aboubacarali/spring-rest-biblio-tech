package fr.formation.spring.bibliotech.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// @ResponseStatus est un fallback. Si l'exception n'est pas gérée par
// un @ExceptionHandler, Spring retournera ce statut.
@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String resourceName, Long id) {
        super(String.format("La ressource '%s' avec l'id '%d' n'a pas été trouvée.",
                resourceName, id));
    }
}

