package infsus.jezik.backend.model.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EnrollmentUpdateForm {
    private String status;
    @Min(value = 1, message = "Grade must be at least 1")
    @Max(value = 5, message = "Grade must be at most 5")
    private Short grade1;
    @Min(value = 1, message = "Grade must be at least 1")
    @Max(value = 5, message = "Grade must be at most 5")
    private Short grade2;
    @Min(value = 1, message = "Grade must be at least 1")
    @Max(value = 5, message = "Grade must be at most 5")
    private Short grade3;
    @Min(value = 1, message = "Grade must be at least 1")
    @Max(value = 5, message = "Grade must be at most 5")
    private Short finalGrade;
}
