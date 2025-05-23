package infsus.jezik.backend.mapper;

import infsus.jezik.backend.model.db.Student;
import infsus.jezik.backend.model.dto.StudentDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StudentMapper {
    StudentDto toDto(Student student);
}
