package infsus.jezik.backend.mapper;

import infsus.jezik.backend.model.db.Professor;
import infsus.jezik.backend.model.dto.ProfessorDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProfessorMapper {
    ProfessorDto toDto(Professor professor);
}
