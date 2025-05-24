package infsus.jezik.backend.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProfessorListItemDto {
    private Long id;
    private String fullName;
}
