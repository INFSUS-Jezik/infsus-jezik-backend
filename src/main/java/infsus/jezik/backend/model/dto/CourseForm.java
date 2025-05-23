package infsus.jezik.backend.model.dto;

import jakarta.validation.constraints.DecimalMin;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class CourseForm {
    private String name;
    private String description;
    @DecimalMin(value = "0.01", message = "Price must be a positive number")
    private BigDecimal price;
    private Long professorId;
}
