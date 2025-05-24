package infsus.jezik.backend.service.impl;

import infsus.jezik.backend.mapper.EnrollmentMapper;
import infsus.jezik.backend.model.db.Course;
import infsus.jezik.backend.model.db.Enrollment;
import infsus.jezik.backend.model.db.EnrollmentStatus;
import infsus.jezik.backend.model.db.Student;
import infsus.jezik.backend.model.dto.EnrollmentDto;
import infsus.jezik.backend.model.dto.EnrollmentForm;
import infsus.jezik.backend.model.dto.EnrollmentUpdateForm;
import infsus.jezik.backend.repository.CourseRepository;
import infsus.jezik.backend.repository.EnrollmentRepository;
import infsus.jezik.backend.repository.StudentRepository;
import infsus.jezik.backend.service.EnrollmentService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class EnrollmentServiceImpl implements EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;
    private final EnrollmentMapper enrollmentMapper;

    @Override
    public EnrollmentDto createEnrollment(Long courseId, EnrollmentForm form) {
        Student student = studentRepository.findById(form.getStudentId())
                .orElseThrow(() -> new RuntimeException("Student not found"));
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        // Check for existing active enrollment
        List<Enrollment> existing = enrollmentRepository.findAll();
        boolean alreadyEnrolled = existing.stream()
                .anyMatch(e -> e.getStudent().getId().equals(student.getId())
                        && e.getCourse().getId().equals(course.getId())
                        && e.getStatus() == EnrollmentStatus.ACTIVE);
        if (alreadyEnrolled) {
            throw new RuntimeException("Student is already actively enrolled in this course.");
        }

        Enrollment enrollment = new Enrollment();
        enrollment.setStudent(student);
        enrollment.setCourse(course);

        if (form.getEnrollmentDate() != null) {
            enrollment.setEnrollmentDate(OffsetDateTime.parse(form.getEnrollmentDate()));
        } else {
            enrollment.setEnrollmentDate(OffsetDateTime.now());
        }

        if (form.getStatus() != null) {
            enrollment.setStatus(EnrollmentStatus.valueOf(form.getStatus().toUpperCase()));
        } else {
            enrollment.setStatus(EnrollmentStatus.ACTIVE);
        }

        Enrollment saved = enrollmentRepository.save(enrollment);
        return enrollmentMapper.toDto(saved);
    }

    @Override
    public EnrollmentDto updateEnrollment(Long enrollmentId, EnrollmentUpdateForm form) {
        Enrollment enrollment = enrollmentRepository.findById(enrollmentId)
                .orElseThrow(() -> new RuntimeException("Enrollment not found"));

        if (form.getStatus() != null) {
            enrollment.setStatus(EnrollmentStatus.valueOf(form.getStatus().toUpperCase()));
        }
        if (form.getGrade1() != null) enrollment.setGrade1(form.getGrade1());
        if (form.getGrade2() != null) enrollment.setGrade2(form.getGrade2());
        if (form.getGrade3() != null) enrollment.setGrade3(form.getGrade3());
        if (form.getFinalGrade() != null) enrollment.setFinalGrade(form.getFinalGrade());
        if(Objects.equals(form.getStatus(), "COMPLETED") && form.getFinalGrade() == null) {
            throw new RuntimeException("Final grade must be defined if status is completed!");
        }
        Enrollment updated = enrollmentRepository.save(enrollment);
        return enrollmentMapper.toDto(updated);
    }

    @Override
    public void deleteEnrollment(Long enrollmentId) {
        Enrollment enrollment = enrollmentRepository.findById(enrollmentId)
                .orElseThrow(() -> new RuntimeException("Enrollment not found"));
        enrollmentRepository.delete(enrollment);
    }
}
