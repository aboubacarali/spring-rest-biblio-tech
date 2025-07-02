package fr.formation.spring.bibliotech.api.dto;


import lombok.Data;
import java.time.LocalDate;
import java.util.Set;

// DTO pour la représentation d'un livre en sortie.
@Data
public class BookDto {
    private Long id;
    private String title;
    private String isbn;
    private LocalDate publicationDate;
    // On peut aussi utiliser un DTO pour l'auteur ici !
// Pour l'instant, gardons le nom.
    private Set<String> authorNames;
}


