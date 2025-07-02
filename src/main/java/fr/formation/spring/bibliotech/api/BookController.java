package fr.formation.spring.bibliotech.api;


import fr.formation.spring.bibliotech.api.dto.BookDto;
import fr.formation.spring.bibliotech.api.dto.BookSaveDto;
import fr.formation.spring.bibliotech.api.mapper.BookMapper;
import fr.formation.spring.bibliotech.dal.entities.Book;
import fr.formation.spring.bibliotech.dal.repositories.BookRepository;
import fr.formation.spring.bibliotech.dal.spec.BookSpecification;
import fr.formation.spring.bibliotech.exceptions.DuplicateResourceException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

// @RestController combine @Controller et @ResponseBody.
// Il indique que cette classe gère des requêtes web et que les
// retours de méthodes seront directement écrits dans la réponse HTTP.
@RestController
// @RequestMapping définit l'URI de base pour toutes les méthodes
// de ce contrôleur.
@RequestMapping("/api/books")
public class BookController {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    // Injection de dépendance du repository pour accéder aux données.
    public BookController(BookRepository bookRepository, BookMapper bookMapper) {
        this.bookRepository = bookRepository;
        this.bookMapper = bookMapper;
    }

    // L'endpoint GET retourne maintenant une liste de BookDto
    @GetMapping
    public List<BookDto> getAllBooks() {
        return this.bookRepository.findAll().stream()
                .map(this.bookMapper::toDto) // On mappe chaque livre
                .collect(Collectors.toList());
    }
    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        // @PathVariable lie la variable "id" de l'URL au paramètre "id"
        // de la méthode.

        // findById retourne un Optional<Book>, qui est un conteneur
        // pouvant contenir ou non une valeur. C'est une façon propre de
        // gérer les cas où le livre n'est pas trouvé.
        Optional<Book> book = this.bookRepository.findById(id);

        if (book.isPresent()) {
            // Si le livre existe, on retourne un statut 200 OK
            // et le livre dans le corps de la réponse.
            return ResponseEntity.ok(book.get());
        } else {
            // Si le livre n'existe pas, on retourne un statut 404 Not Found.
            // .build() crée une réponse sans corps.
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Book> createBook(@RequestBody BookSaveDto bookToCreate) {
        // @RequestBody dit à Spring de convertir le JSON du corps de la
        // requête en un objet Book.
        if (bookRepository.existsByIsbn(bookToCreate.getIsbn())) {
            throw new DuplicateResourceException("Book", "isbn", bookToCreate.getIsbn());
        }

        Book savedBook = this.bookRepository.save(bookMapper.toEntity(bookToCreate));

        // Bonne pratique REST : lors d'une création (POST), on retourne
        // un statut 201 Created. On fournit aussi dans l'en-tête "Location"
        // l'URL pour accéder à la nouvelle ressource.
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest() // Prend l'URL de la requête actuelle (/api/books)
                .path("/{id}")        // Ajoute /{id}
                .buildAndExpand(savedBook.getId()) // Remplace {id} par l'ID du livre créé
                .toUri();

        return ResponseEntity.created(location).body(savedBook);
    }

    @GetMapping("/search") // Un endpoint dédié pour la recherche
    public Page<BookDto> searchBooks(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String authorLastName,
            @RequestParam(required = false) LocalDate publishedAfter,
            Pageable pageable) {

        Specification<Book> spec = Specification.where(null); // Spec de base

        if (title != null && !title.isEmpty()) {
            spec = spec.and(BookSpecification.hasTitle(title));
        }
        if (authorLastName != null && !authorLastName.isEmpty()) {
            spec = spec.and(BookSpecification.hasAuthorLastName(authorLastName));
        }
        if (publishedAfter != null) {
            spec = spec.and(BookSpecification.publishedAfter(publishedAfter));
        }

        return this.bookRepository.findAll(spec, pageable)
                .map(this.bookMapper::toDto);
    }
}


