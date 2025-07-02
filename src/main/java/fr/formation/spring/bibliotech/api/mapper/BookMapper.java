package fr.formation.spring.bibliotech.api.mapper;

import fr.formation.spring.bibliotech.api.dto.BookDto;
import fr.formation.spring.bibliotech.api.dto.BookSaveDto;
import fr.formation.spring.bibliotech.dal.entities.Author;
import fr.formation.spring.bibliotech.dal.entities.Book;
import fr.formation.spring.bibliotech.dal.repositories.AuthorRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

// componentModel="spring" génère un @Component sur l'implémentation,
// la rendant injectable.

@Mapper(componentModel = "spring")
public abstract class BookMapper {

    @Autowired
    protected AuthorRepository authorRepository;

    // MapStruct gère automatiquement les champs avec le même nom.
    // Pour "authorNames", le mapping est complexe, on le spécifie.

    @Mapping(target = "authorNames", source = "authors", qualifiedByName = "authorsToNames")
    public abstract BookDto toDto(Book entity);

    // Pour le DTO -> Entité, on doit gérer la conversion des IDs en entités.
    @Mapping(target = "authors", source = "authorIds")
    public abstract Book toEntity(BookSaveDto dto);

    // Cette méthode nommée "authorsToNames" sera utilisée par MapStruct
    // pour le mapping personnalisé.
    @Named("authorsToNames")
    protected Set<String> authorsToNames(Set<Author> authors) {
        if (authors == null) {
            return null;
        }
        return authors.stream()
                .map(author -> author.getFirstName() + " " + author.getLastName())
                .collect(Collectors.toSet());
    }

    protected Author authorFromId(Long id) throws ResourceNotFoundException, IllegalArgumentException {
        if(id == null) {
            throw new IllegalArgumentException("Un id auteur ne peut être null");
        }

        return authorRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Auteur introuvable")
        );
    }

    protected Set<Author> mapAuthorIdsToAuthors(Set<Long> authorIds) {
        if (authorIds == null || authorIds.isEmpty()) {
            return Collections.emptySet();
        }

        return authorIds
                .stream()
                .map(this::authorFromId)
                .collect(Collectors.toSet());

    }
}