package fr.formation.spring.bibliotech.dal.spec;


import fr.formation.spring.bibliotech.dal.entities.Book;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class BookSpecification {

    // Spécification pour filtrer par titre (recherche partielle)
    public static Specification<Book> hasTitle(String title) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(root.get("title"), "%" + title + "%");
    }

    // Spécification pour filtrer par date de publication (après une date)
    public static Specification<Book> publishedAfter(LocalDate date) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.greaterThanOrEqualTo(root.get("publicationDate"), date);
    }

    // Spécification pour filtrer par nom de l'auteur
    public static Specification<Book> hasAuthorLastName(String lastName) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(root.join("authors").get("lastName"), "%" + lastName + "%");
    }
}


