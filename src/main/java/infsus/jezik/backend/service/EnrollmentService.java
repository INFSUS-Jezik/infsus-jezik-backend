package infsus.jezik.backend.service;

import infsus.jezik.backend.model.dto.EnrollmentDto;
import infsus.jezik.backend.model.dto.EnrollmentForm;
import infsus.jezik.backend.model.dto.EnrollmentUpdateForm;

public interface EnrollmentService {
    EnrollmentDto createEnrollment(Long courseId, EnrollmentForm form);
    EnrollmentDto updateEnrollment(Long enrollmentId, EnrollmentUpdateForm form);
    void deleteEnrollment(Long enrollmentId);
}
