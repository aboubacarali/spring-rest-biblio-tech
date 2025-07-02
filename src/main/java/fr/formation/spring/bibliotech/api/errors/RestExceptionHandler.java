package fr.formation.spring.bibliotech.api.errors;

// package fr.formation.spring.bibliotech.api.errors;

import fr.formation.spring.bibliotech.api.dto.ErrorDto;
import fr.formation.spring.bibliotech.exceptions.DuplicateResourceException;
import fr.formation.spring.bibliotech.exceptions.ResourceNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class RestExceptionHandler {

    // Gère notre exception personnalisée pour les ressources non trouvées
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDto> handleResourceNotFound(
            ResourceNotFoundException ex, HttpServletRequest request) {

        ErrorDto errorDto = new ErrorDto(
                HttpStatus.NOT_FOUND.value(),
                "Resource Not Found",
                ex.getMessage(),
                request.getRequestURI());

        return new ResponseEntity<>(errorDto, HttpStatus.NOT_FOUND);
    }

    // Gère les erreurs de validation de @Valid
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDto> handleValidationExceptions(
            MethodArgumentNotValidException ex, HttpServletRequest request) {

        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );

        ErrorDto errorDto = new ErrorDto(
                HttpStatus.BAD_REQUEST.value(),
                "Validation Error",
                "Un ou plusieurs champs sont invalides.",
                request.getRequestURI());
        errorDto.setValidationErrors(errors);

        return new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<ErrorDto> handleDuplicateResource(
            DuplicateResourceException ex, HttpServletRequest request) {

        ErrorDto errorDto = new ErrorDto(
                HttpStatus.CONFLICT.value(),
                "Duplicate Resource",
                ex.getMessage(),
                request.getRequestURI());

        return new ResponseEntity<>(errorDto, HttpStatus.CONFLICT);
    }
}


