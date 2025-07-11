package infsus.jezik.backend.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClassroomDto {
    private Long id;
    private String name;
    private String abbreviation;
}
