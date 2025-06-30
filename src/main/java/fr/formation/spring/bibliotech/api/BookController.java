package fr.formation.spring.bibliotech.api;


import fr.formation.spring.bibliotech.dal.entities.Book;
import fr.formation.spring.bibliotech.dal.repositories.BookRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

// @RestController combine @Controller et @ResponseBody.
// Il indique que cette classe gère des requêtes web et que les
// retours de méthodes seront directement écrits dans la réponse HTTP.
@RestController
// @RequestMapping définit l'URI de base pour toutes les méthodes
// de ce contrôleur.
@RequestMapping("/api/books")
public class BookController {

    private final BookRepository bookRepository;

    // Injection de dépendance du repository pour accéder aux données.
    public BookController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    // @GetMapping indique que cette méthode répond aux requêtes HTTP GET.
    // L'URL complète sera donc : GET /api/books
    @GetMapping
    public List<Book> getAllBooks() {
        // Spring Data JPA nous offre findAll() pour récupérer tous les
        // enregistrements.
        return this.bookRepository.findAll();
    }
}


