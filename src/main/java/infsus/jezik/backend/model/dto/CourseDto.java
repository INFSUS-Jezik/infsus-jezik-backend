package infsus.jezik.backend.model.dto;


import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.util.Set;

@Data
@NoArgsConstructor
public class CourseDto {
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private ProfessorDto professor;
    private Set<EnrollmentDto> enrollments;
    private Set<ScheduleDto> schedules;
}
