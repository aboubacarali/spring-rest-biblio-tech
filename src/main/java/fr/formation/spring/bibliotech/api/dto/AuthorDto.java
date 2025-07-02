package fr.formation.spring.bibliotech.api.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthorDto extends RepresentationModel<AuthorDto> {
    private Long id;
    private String firstName;
    private String lastName;
}
