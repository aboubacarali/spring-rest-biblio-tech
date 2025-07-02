package fr.formation.spring.bibliotech.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class DuplicateResourceException extends RuntimeException {

    public DuplicateResourceException(String ressourceName, String fieldName, Object fieldValue) {
        super(String.format("La ressource '%s' avec %s = '%s' existe déjà", ressourceName, fieldName, fieldValue));
    }
}
