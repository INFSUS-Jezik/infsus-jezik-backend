package infsus.jezik.backend;

import com.fasterxml.jackson.databind.ObjectMapper;
import infsus.jezik.backend.model.db.*;
import infsus.jezik.backend.model.dto.ScheduleForm;

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
import java.time.LocalTime;
import java.util.Set;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class ScheduleControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private ProfessorRepository professorRepository;

    @Autowired
    private ClassroomRepository classroomRepository;

    private Course course;
    private Classroom classroom;

    @BeforeEach
    void setUp() {
        scheduleRepository.deleteAll();
        courseRepository.deleteAll();
        professorRepository.deleteAll();
        classroomRepository.deleteAll();

        Professor professor = new Professor();
        professor.setFirstName("Tomislav");
        professor.setLastName("Babić");
        professor.setEmail("tomislav.babic@fer.hr");
        professor.setPasswordHash("pass");
        professor.setUserStatus(UserStatus.ACTIVE);
        professor.setBio("Predaje Algoritme");
        professor.setRegistrationDate(LocalDateTime.now());
        professor = professorRepository.save(professor);

        classroom = new Classroom();
        classroom.setName("Dvorana D1");
        classroom.setAbbreviation("D1");
        classroom = classroomRepository.save(classroom);

        course = new Course();
        course.setName("Algoritmi i Strukture Podataka");
        course.setDescription("Teorija i praksa algoritama");
        course.setPrice(new BigDecimal("180.00"));
        course.setProfessor(professor);
        course.setEnrollments(Set.of());
        course.setSchedules(Set.of());
        course = courseRepository.save(course);
    }

    @Test
    void testCreateSchedule() throws Exception {
        ScheduleForm form = new ScheduleForm();
        form.setClassroomId(classroom.getId());
        form.setDayOfWeek(2);
        form.setStartTime("10:00");
        form.setEndTime("12:00");

        String json = objectMapper.writeValueAsString(form);

        mockMvc.perform(post("/api/courses/{courseId}/schedules", course.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.dayOfWeek").value(2))
                .andExpect(jsonPath("$.classroom.name").value("Dvorana D1"));
    }

    @Test
    void testUpdateSchedule() throws Exception {
        // Create existing schedule
        Schedule schedule = new Schedule();
        schedule.setCourse(course);
        schedule.setClassroom(classroom);
        schedule.setDayOfWeek(2);
        schedule.setStartTime(LocalTime.of(10, 0));
        schedule.setEndTime(LocalTime.of(12, 0));
        schedule = scheduleRepository.save(schedule);

        ScheduleForm form = new ScheduleForm();
        form.setClassroomId(classroom.getId());
        form.setDayOfWeek(2);
        form.setStartTime("13:00");
        form.setEndTime("15:00");

        String json = objectMapper.writeValueAsString(form);

        mockMvc.perform(put("/api/schedules/{id}", schedule.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.startTime").value("13:00:00"))
                .andExpect(jsonPath("$.endTime").value("15:00:00"));
    }

    @Test
    void testDeleteSchedule() throws Exception {
        Schedule schedule = new Schedule();
        schedule.setCourse(course);
        schedule.setClassroom(classroom);
        schedule.setDayOfWeek(3);
        schedule.setStartTime(LocalTime.of(14, 0));
        schedule.setEndTime(LocalTime.of(16, 0));
        schedule = scheduleRepository.save(schedule);

        mockMvc.perform(delete("/api/schedules/{id}", schedule.getId()))
                .andExpect(status().isNoContent());
    }
}

