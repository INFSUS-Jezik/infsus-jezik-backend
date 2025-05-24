package infsus.jezik.backend;

import com.fasterxml.jackson.databind.ObjectMapper;
import infsus.jezik.backend.model.db.*;
import infsus.jezik.backend.model.dto.EnrollmentDto;
import infsus.jezik.backend.model.dto.EnrollmentForm;
import infsus.jezik.backend.model.dto.EnrollmentUpdateForm;


import infsus.jezik.backend.repository.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.Set;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class EnrollmentControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private ProfessorRepository professorRepository;

    @Autowired
    private CourseRepository courseRepository;

    private Course course;
    private Student student;

    @BeforeEach
    void setUp() {
        enrollmentRepository.deleteAll();
        courseRepository.deleteAll();
        professorRepository.deleteAll();
        studentRepository.deleteAll();

        Professor professor = new Professor();
        professor.setFirstName("Ivan");
        professor.setLastName("Kos");
        professor.setEmail("ivan.kos@fer.hr");
        professor.setPasswordHash("pass");
        professor.setUserStatus(UserStatus.ACTIVE);
        professor.setBio("Predaje Osnove računarstva");
        professor.setRegistrationDate(LocalDateTime.now());
        professor = professorRepository.save(professor);

        course = new Course();
        course.setName("Osnove računarstva");
        course.setDescription("Uvod u digitalnu logiku");
        course.setPrice(new BigDecimal("150.00"));
        course.setProfessor(professor);
        course.setEnrollments(Set.of());
        course.setSchedules(Set.of());
        course = courseRepository.save(course);

        student = new Student();
        student.setFirstName("Marko");
        student.setLastName("Horvat");
        student.setEmail("marko.horvat@fer.hr");
        student.setPasswordHash("studentpass");
        student.setUserStatus(UserStatus.ACTIVE);
        student.setAdditionalContact("0912345678");
        student.setRegistrationDate(LocalDateTime.now());
        student = studentRepository.save(student);
    }

    @Test
    void testCreateEnrollment() throws Exception {
        EnrollmentForm form = new EnrollmentForm();
        form.setStudentId(student.getId());
        form.setStatus("ACTIVE");
        form.setEnrollmentDate(OffsetDateTime.now().toString());

        String json = objectMapper.writeValueAsString(form);

        mockMvc.perform(post("/api/courses/{courseId}/enrollments", course.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.student.firstName").value("Marko"))
                .andExpect(jsonPath("$.status").value("ACTIVE"));
    }

    @Test
    void testUpdateEnrollment() throws Exception {
        // create enrollment manually
        Enrollment enrollment = new Enrollment();
        enrollment.setCourse(course);
        enrollment.setStudent(student);
        enrollment.setStatus(EnrollmentStatus.ACTIVE);
        enrollment.setEnrollmentDate(OffsetDateTime.now());
        enrollment = enrollmentRepository.save(enrollment);

        EnrollmentUpdateForm form = new EnrollmentUpdateForm();
        form.setStatus("COMPLETED");
        form.setFinalGrade((short) 5);

        String json = objectMapper.writeValueAsString(form);

        mockMvc.perform(put("/api/enrollments/{id}", enrollment.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("COMPLETED"))
                .andExpect(jsonPath("$.finalGrade").value(5));
    }

    @Test
    void testDeleteEnrollment() throws Exception {
        Enrollment enrollment = new Enrollment();
        enrollment.setCourse(course);
        enrollment.setStudent(student);
        enrollment.setStatus(EnrollmentStatus.ACTIVE);
        enrollment.setEnrollmentDate(OffsetDateTime.now());
        enrollment = enrollmentRepository.save(enrollment);

        mockMvc.perform(delete("/api/enrollments/{id}", enrollment.getId()))
                .andExpect(status().isNoContent());
    }
}

