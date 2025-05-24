package infsus.jezik.backend.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

@Data
@NoArgsConstructor
public class EnrollmentDto {
    private Long id;
    private StudentDto student;
    private OffsetDateTime enrollmentDate;
    private String status;
    private Short grade1;
    private Short grade2;
    private Short grade3;
    private Short finalGrade;
}