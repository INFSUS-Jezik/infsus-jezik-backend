package infsus.jezik.backend.mapper;

import infsus.jezik.backend.model.db.Enrollment;
import infsus.jezik.backend.model.dto.EnrollmentDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = StudentMapper.class)
public interface EnrollmentMapper {

    @Mapping(source = "status", target = "status")
    EnrollmentDto toDto(Enrollment enrollment);
}
