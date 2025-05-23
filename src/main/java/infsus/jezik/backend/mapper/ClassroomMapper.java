package infsus.jezik.backend.mapper;

import infsus.jezik.backend.model.db.Classroom;
import infsus.jezik.backend.model.dto.ClassroomDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ClassroomMapper {
    ClassroomDto toDto(Classroom classroom);
}
