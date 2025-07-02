package fr.formation.spring.bibliotech.api.dto;


import lombok.Data;
import java.time.LocalDate;
import java.util.Set;

// DTO pour les données de création/mise à jour d'un livre.
@Data
public class BookSaveDto {
    private String title;
    private String isbn;
    private LocalDate publicationDate;
    // Pour créer un livre, le client nous donne les IDs des auteurs.
    private Set<Long> authorIds;
}


