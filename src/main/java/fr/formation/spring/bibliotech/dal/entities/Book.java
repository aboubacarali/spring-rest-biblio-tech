package fr.formation.spring.bibliotech.dal.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(unique = true) // L'ISBN doit être unique
    private String isbn;

    private LocalDate publicationDate;

    // Un livre peut avoir plusieurs auteurs.
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable( // Table de jointure pour la relation ManyToMany
            name = "books_authors",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id"))
    private Set<Author> authors;
}
