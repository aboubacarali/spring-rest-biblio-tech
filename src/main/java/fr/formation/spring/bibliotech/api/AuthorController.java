package fr.formation.spring.bibliotech.api;

import fr.formation.spring.bibliotech.api.dto.AuthorDto;
import fr.formation.spring.bibliotech.api.dto.AuthorSaveDto;
import fr.formation.spring.bibliotech.api.mapper.AuthorMapper;
import fr.formation.spring.bibliotech.dal.entities.Author;
import fr.formation.spring.bibliotech.dal.entities.Book;
import fr.formation.spring.bibliotech.dal.repositories.AuthorRepository;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/authors")
public class AuthorController {
    private final AuthorRepository authorRepository;
    private final AuthorMapper authorMapper;

    public AuthorController(AuthorRepository authorRepository, AuthorMapper authorMapper) {
        this.authorRepository = authorRepository;
        this.authorMapper = authorMapper;
    }

    @GetMapping
    public List<AuthorDto> getAllAuthors() {
//        return authorRepository.findAll().;
        List<AuthorDto> authorsDtos = authorRepository.findAll()
                .stream()
                .map(authorMapper::toDto)
                .collect(Collectors.toList());
        return authorsDtos;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Author> getAuthorById(@PathVariable Long id) {
        Optional<Author> author = this.authorRepository.findById(id);
        if (author.isPresent()) {
            return ResponseEntity.ok(author.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Author> createAuthor(@RequestBody @Valid AuthorSaveDto authorToCreate) {
        Author savedAuthor = this.authorRepository.save(authorMapper.toEntity(authorToCreate));
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedAuthor.getId())
                .toUri();

        return ResponseEntity.created(location).body(savedAuthor);
    }
}
