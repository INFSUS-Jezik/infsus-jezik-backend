package infsus.jezik.backend.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ClassroomDto {
    private Long id;
    private String name;
    private String abbreviation;
}
