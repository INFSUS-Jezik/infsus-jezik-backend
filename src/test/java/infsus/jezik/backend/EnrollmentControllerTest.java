package infsus.jezik.backend;

import com.fasterxml.jackson.databind.ObjectMapper;
import infsus.jezik.backend.controller.EnrollmentController;
import infsus.jezik.backend.model.dto.EnrollmentDto;
import infsus.jezik.backend.model.dto.EnrollmentForm;
import infsus.jezik.backend.model.dto.EnrollmentUpdateForm;
import infsus.jezik.backend.service.EnrollmentService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.OffsetDateTime;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(EnrollmentController.class)
class EnrollmentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EnrollmentService enrollmentService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createEnrollment_createsNew() throws Exception {
        EnrollmentForm form = new EnrollmentForm();
        form.setStudentId(1L);
        form.setEnrollmentDate("2024-10-01T09:00:00Z");
        form.setStatus("ACTIVE");

        EnrollmentDto dto = new EnrollmentDto();
        dto.setId(10L);
        dto.setStatus("ACTIVE");
        dto.setEnrollmentDate(OffsetDateTime.parse("2024-10-01T09:00:00Z"));

        given(enrollmentService.createEnrollment(eq(5L), any(EnrollmentForm.class))).willReturn(dto);

        mockMvc.perform(post("/api/courses/{courseId}/enrollments", 5L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(form)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(10L))
                .andExpect(jsonPath("$.status").value("ACTIVE"))
                .andExpect(jsonPath("$.enrollmentDate").value("2024-10-01T09:00:00Z"));
    }

    @Test
    void updateEnrollment_updatesData() throws Exception {
        EnrollmentUpdateForm form = new EnrollmentUpdateForm();
        form.setStatus("COMPLETED");
        form.setGrade1((short) 5);
        form.setGrade2((short) 4);
        form.setGrade3((short) 5);
        form.setFinalGrade((short) 5);

        EnrollmentDto dto = new EnrollmentDto();
        dto.setId(10L);
        dto.setStatus("COMPLETED");
        dto.setGrade1((short) 5);
        dto.setGrade2((short) 4);
        dto.setGrade3((short) 5);
        dto.setFinalGrade((short) 5);

        given(enrollmentService.updateEnrollment(eq(10L), any(EnrollmentUpdateForm.class))).willReturn(dto);

        mockMvc.perform(put("/api/enrollments/{enrollmentId}", 10L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(form)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(10L))
                .andExpect(jsonPath("$.status").value("COMPLETED"))
                .andExpect(jsonPath("$.grade1").value(5))
                .andExpect(jsonPath("$.finalGrade").value(5));
    }

    @Test
    void deleteEnrollment_deletesSuccessfully() throws Exception {
        willDoNothing().given(enrollmentService).deleteEnrollment(10L);

        mockMvc.perform(delete("/api/enrollments/{enrollmentId}", 10L))
                .andExpect(status().isNoContent());
    }
}

