package infsus.jezik.backend.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProfessorDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String bio;
}
