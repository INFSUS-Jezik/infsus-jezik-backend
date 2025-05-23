package infsus.jezik.backend.mapper;


import infsus.jezik.backend.model.db.Course;
import infsus.jezik.backend.model.dto.CourseDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {
        ProfessorMapper.class,
        EnrollmentMapper.class,
        ScheduleMapper.class
})
public interface CourseMapper {
    CourseDto toDto(Course course);
}
