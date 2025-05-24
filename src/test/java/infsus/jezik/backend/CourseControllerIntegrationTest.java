package infsus.jezik.backend;

import com.fasterxml.jackson.databind.ObjectMapper;
import infsus.jezik.backend.model.db.Professor;

import infsus.jezik.backend.model.db.UserStatus;
import infsus.jezik.backend.model.dto.CourseDto;
import infsus.jezik.backend.model.dto.CourseForm;
import infsus.jezik.backend.repository.CourseRepository;
import infsus.jezik.backend.repository.ProfessorRepository;
import infsus.jezik.backend.service.CourseService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class CourseControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CourseService courseService;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private ProfessorRepository professorRepository;

    private CourseForm courseForm;
    private Professor professor;

    @BeforeEach
    void setUp() {
        courseRepository.deleteAll();
        professorRepository.deleteAll();

        professor = new Professor();
        professor.setFirstName("Ivana");
        professor.setLastName("Knežević");
        professor.setEmail("ivana.knezevic@fer.hr");
        professor.setPasswordHash("hashedpass");
        professor.setUserStatus(UserStatus.ACTIVE);
        professor.setBio("Predaje Matematiku");
        professor.setRegistrationDate(LocalDateTime.now());
        professor = professorRepository.save(professor);

        courseForm = new CourseForm();
        courseForm.setName("Matematika 1");
        courseForm.setDescription("Analiza, skupovi, funkcije");
        courseForm.setPrice(new BigDecimal("200.00"));
        courseForm.setProfessorId(professor.getId());
    }

    @Test
    void testCreateCourse() throws Exception {
        String json = objectMapper.writeValueAsString(courseForm);

        mockMvc.perform(post("/api/courses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Matematika 1"))
                .andExpect(jsonPath("$.description").value("Analiza, skupovi, funkcije"))
                .andExpect(jsonPath("$.price").value(200.00));
    }

    @Test
    void testGetAllCourses() throws Exception {
        courseService.createCourse(courseForm);

        mockMvc.perform(get("/api/courses"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Matematika 1"));
    }

    @Test
    void testGetCourseById() throws Exception {
        CourseDto courseDto = courseService.createCourse(courseForm);

        mockMvc.perform(get("/api/courses/{courseId}", courseDto.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(courseDto.getId()))
                .andExpect(jsonPath("$.name").value("Matematika 1"));
    }

    @Test
    void testUpdateCourse() throws Exception {
        CourseDto courseDto = courseService.createCourse(courseForm);

        CourseForm updated = new CourseForm();
        updated.setName("Diskretna matematika");
        updated.setDescription("Skupovi, logika, kombinatorika");
        updated.setPrice(new BigDecimal("210.00"));
        updated.setProfessorId(professor.getId());

        String json = objectMapper.writeValueAsString(updated);

        mockMvc.perform(put("/api/courses/{courseId}", courseDto.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Diskretna matematika"));
    }

    @Test
    void testDeleteCourse() throws Exception {
        CourseDto courseDto = courseService.createCourse(courseForm);

        mockMvc.perform(delete("/api/courses/{courseId}", courseDto.getId()))
                .andExpect(status().isNoContent());
    }
}

