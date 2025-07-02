package fr.formation.spring.bibliotech.api.mapper;

import fr.formation.spring.bibliotech.api.dto.AuthorDto;
import fr.formation.spring.bibliotech.api.dto.AuthorSaveDto;
import fr.formation.spring.bibliotech.dal.entities.Author;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuthorMapper {
    AuthorDto toDto(Author author);

    Author toEntity(AuthorSaveDto authorSaveDto);
}
