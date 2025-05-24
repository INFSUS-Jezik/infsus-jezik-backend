package infsus.jezik.backend;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import infsus.jezik.backend.controller.CourseController;
import infsus.jezik.backend.model.dto.CourseDto;
import infsus.jezik.backend.model.dto.CourseForm;
import infsus.jezik.backend.model.dto.ProfessorListItemDto;
import infsus.jezik.backend.service.CourseService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


import java.math.BigDecimal;
import java.util.List;



import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CourseController.class)
class CourseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CourseService courseService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getCourses_returnsListOfCourses() throws Exception {
        CourseDto course = new CourseDto();
        course.setId(1L);
        course.setName("English 101");
        course.setDescription("Essential English language course");
        course.setPrice(new BigDecimal("0.00"));
        course.setProfessor(new ProfessorListItemDto(1L, "Maja Matetić"));

        given(courseService.getCourses(null)).willReturn(List.of(course));

        mockMvc.perform(get("/api/courses"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].name").value("English 101"))
                .andExpect(jsonPath("$[0].description").value("Essential English language course"))
                .andExpect(jsonPath("$[0].professor.id").value(1L))
                .andExpect(jsonPath("$[0].professor.fullName").value("Maja Matetić"));
    }

    @Test
    void getCourseById_returnsCourse() throws Exception {
        CourseDto course = new CourseDto();
        course.setId(1L);
        course.setName("English 101");
        course.setDescription("Essential English language course");
        course.setPrice(new BigDecimal("0.00"));
        course.setProfessor(new ProfessorListItemDto(1L, "Maja Matetić"));

        given(courseService.getCourseById(1L)).willReturn(course);

        mockMvc.perform(get("/api/courses/{courseId}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("English 101"))
                .andExpect(jsonPath("$.professor.fullName").value("Maja Matetić"));
    }

    @Test
    void createCourse_createsCourse() throws Exception {
        CourseForm form = new CourseForm();
        form.setName("Novi kolegij");
        form.setDescription("Opis kolegija");
        form.setPrice(new BigDecimal("200.00"));
        form.setProfessorId(1L);

        CourseDto courseDto = new CourseDto();
        courseDto.setId(2L);
        courseDto.setName("Novi kolegij");
        courseDto.setDescription("Opis kolegija");
        courseDto.setPrice(new BigDecimal("200.00"));
        courseDto.setProfessor(new ProfessorListItemDto(1L, "Ivan Kovačić"));

        given(courseService.createCourse(any(CourseForm.class))).willReturn(courseDto);

        mockMvc.perform(post("/api/courses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(form)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(2L))
                .andExpect(jsonPath("$.name").value("Novi kolegij"));
    }

    @Test
    void updateCourse_updatesCourse() throws Exception {
        CourseForm form = new CourseForm();
        form.setName("Ažurirani kolegij");
        form.setDescription("Novo ažurirano");
        form.setPrice(new BigDecimal("150.00"));
        form.setProfessorId(2L);

        CourseDto courseDto = new CourseDto();
        courseDto.setId(1L);
        courseDto.setName("Ažurirani kolegij");
        courseDto.setDescription("Novo ažurirano");
        courseDto.setPrice(new BigDecimal("150.00"));
        courseDto.setProfessor(new ProfessorListItemDto(2L, "Ivana Novak"));

        given(courseService.updateCourse(eq(1L), any(CourseForm.class))).willReturn(courseDto);

        mockMvc.perform(put("/api/courses/{courseId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(form)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Ažurirani kolegij"));
    }

    @Test
    void deleteCourse_deletesSuccessfully() throws Exception {
        willDoNothing().given(courseService).deleteCourse(1L);

        mockMvc.perform(delete("/api/courses/{courseId}", 1L))
                .andExpect(status().isNoContent());
    }
}
