package infsus.jezik.backend.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EnrollmentForm {
    private Long studentId;
    private String enrollmentDate;
    private String status;
}
