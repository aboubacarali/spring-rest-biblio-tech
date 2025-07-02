package fr.formation.spring.bibliotech.api.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthorSaveDto {
    @NotBlank(message = "The first name field can not be blank")
    private String firstName;

    @NotBlank(message = "The lastname field can not be blank")
    private String lastName;
}
