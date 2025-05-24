package infsus.jezik.backend;

import infsus.jezik.backend.model.db.*;
import infsus.jezik.backend.model.dto.*;
import infsus.jezik.backend.mapper.EnrollmentMapper;
import infsus.jezik.backend.repository.*;
import infsus.jezik.backend.service.impl.EnrollmentServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;


@ExtendWith(MockitoExtension.class)
class EnrollmentServiceTest {

    @Mock private EnrollmentRepository enrollmentRepository;
    @Mock private StudentRepository studentRepository;
    @Mock private CourseRepository courseRepository;
    @Mock private EnrollmentMapper enrollmentMapper;

    @InjectMocks
    private EnrollmentServiceImpl enrollmentService;

    private Student student;
    private Course course;
    private Enrollment enrollment;
    private EnrollmentDto enrollmentDto;

    @BeforeEach
    void setUp() {
        student = new Student();
        student.setId(1L);

        course = new Course();
        course.setId(10L);

        enrollment = new Enrollment();
        enrollment.setId(100L);
        enrollment.setStudent(student);
        enrollment.setCourse(course);
        enrollment.setStatus(EnrollmentStatus.ACTIVE);

        enrollmentDto = new EnrollmentDto();
        enrollmentDto.setId(100L);
        enrollmentDto.setStatus("ACTIVE");
    }

    @Test
    void createEnrollment_success() {
        EnrollmentForm form = new EnrollmentForm();
        form.setStudentId(1L);
        form.setStatus("ACTIVE");
        form.setEnrollmentDate("2024-10-01T10:00:00Z");

        given(studentRepository.findById(1L)).willReturn(Optional.of(student));
        given(courseRepository.findById(10L)).willReturn(Optional.of(course));
        given(enrollmentRepository.findAll()).willReturn(List.of());
        given(enrollmentRepository.save(any())).willReturn(enrollment);
        given(enrollmentMapper.toDto(enrollment)).willReturn(enrollmentDto);

        EnrollmentDto result = enrollmentService.createEnrollment(10L, form);

        assertEquals("ACTIVE", result.getStatus());
        verify(enrollmentRepository).save(any());
    }

    @Test
    void createEnrollment_alreadyEnrolled_shouldThrow() {
        EnrollmentForm form = new EnrollmentForm();
        form.setStudentId(1L);
        form.setStatus("ACTIVE");

        given(studentRepository.findById(1L)).willReturn(Optional.of(student));
        given(courseRepository.findById(10L)).willReturn(Optional.of(course));
        given(enrollmentRepository.findAll()).willReturn(List.of(enrollment));

        assertThrows(RuntimeException.class, () -> enrollmentService.createEnrollment(10L, form));
    }

    @Test
    void updateEnrollment_success() {
        EnrollmentUpdateForm form = new EnrollmentUpdateForm();
        form.setStatus("COMPLETED");
        form.setGrade1((short) 4);
        form.setGrade2((short) 5);
        form.setFinalGrade((short) 5);

        given(enrollmentRepository.findById(100L)).willReturn(Optional.of(enrollment));
        given(enrollmentRepository.save(enrollment)).willReturn(enrollment);
        given(enrollmentMapper.toDto(enrollment)).willReturn(enrollmentDto);

        EnrollmentDto result = enrollmentService.updateEnrollment(100L, form);

        assertEquals("ACTIVE", result.getStatus());
        verify(enrollmentRepository).save(enrollment);
    }

    @Test
    void updateEnrollment_missingFinalGrade_shouldThrow() {
        EnrollmentUpdateForm form = new EnrollmentUpdateForm();
        form.setStatus("COMPLETED");

        given(enrollmentRepository.findById(100L)).willReturn(Optional.of(enrollment));

        assertThrows(RuntimeException.class, () -> enrollmentService.updateEnrollment(100L, form));
    }

    @Test
    void deleteEnrollment_success() {
        given(enrollmentRepository.findById(100L)).willReturn(Optional.of(enrollment));

        enrollmentService.deleteEnrollment(100L);

        verify(enrollmentRepository).delete(enrollment);
    }

    @Test
    void deleteEnrollment_notFound_shouldThrow() {
        given(enrollmentRepository.findById(100L)).willReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> enrollmentService.deleteEnrollment(100L));
    }
}

