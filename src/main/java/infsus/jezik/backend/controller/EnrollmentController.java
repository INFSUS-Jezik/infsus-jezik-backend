package infsus.jezik.backend.controller;

import infsus.jezik.backend.model.dto.EnrollmentDto;
import infsus.jezik.backend.model.dto.EnrollmentForm;
import infsus.jezik.backend.model.dto.EnrollmentUpdateForm;
import infsus.jezik.backend.service.EnrollmentService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class EnrollmentController {

    private EnrollmentService enrollmentService;

    @PostMapping("/api/courses/{courseId}/enrollments")
    public EnrollmentDto createEnrollment(@PathVariable Long courseId, @RequestBody EnrollmentForm form) {
        return enrollmentService.createEnrollment(courseId, form);
    }

    @PutMapping("/api/enrollments/{enrollmentId}")
    public EnrollmentDto updateEnrollment(@PathVariable Long enrollmentId, @RequestBody @Valid EnrollmentUpdateForm form) {
        return enrollmentService.updateEnrollment(enrollmentId, form);
    }

    @DeleteMapping("/api/enrollments/{enrollmentId}")
    public ResponseEntity<Void> deleteEnrollment(@PathVariable Long enrollmentId) {
        enrollmentService.deleteEnrollment(enrollmentId);
        return ResponseEntity.noContent().build();
    }
}
