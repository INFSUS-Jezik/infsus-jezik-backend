package infsus.jezik.backend.mapper;

import infsus.jezik.backend.model.db.Professor;
import infsus.jezik.backend.model.dto.ProfessorDto;
import infsus.jezik.backend.model.dto.ProfessorListItemDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProfessorMapper {
    ProfessorDto toDto(Professor professor);


    @Mapping(target = "fullName", expression = "java(professor.getFirstName() + \" \" + professor.getLastName())")
    ProfessorListItemDto toDtoListItem(Professor professor);
}
