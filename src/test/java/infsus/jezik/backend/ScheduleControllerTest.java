package infsus.jezik.backend;

import com.fasterxml.jackson.databind.ObjectMapper;
import infsus.jezik.backend.controller.ScheduleController;
import infsus.jezik.backend.model.dto.ScheduleDto;
import infsus.jezik.backend.model.dto.ScheduleForm;
import infsus.jezik.backend.model.dto.ClassroomDto;
import infsus.jezik.backend.service.ScheduleService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalTime;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(ScheduleController.class)
class ScheduleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ScheduleService scheduleService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createSchedule_createsNew() throws Exception {
        ScheduleForm form = new ScheduleForm();
        form.setClassroomId(1L);
        form.setDayOfWeek(2);
        form.setStartTime("10:00");
        form.setEndTime("12:00");

        ScheduleDto dto = new ScheduleDto();
        dto.setId(1L);
        dto.setDayOfWeek(2);
        dto.setStartTime(LocalTime.of(10, 0));
        dto.setEndTime(LocalTime.of(12, 0));
        dto.setClassroom(new ClassroomDto(1L, "Dvorana A101", "A101"));

        given(scheduleService.createSchedule(eq(5L), any(ScheduleForm.class))).willReturn(dto);

        mockMvc.perform(post("/api/courses/{courseId}/schedules", 5L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(form)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.dayOfWeek").value(2))
                .andExpect(jsonPath("$.startTime").value("10:00:00"))
                .andExpect(jsonPath("$.classroom.name").value("Dvorana A101"));
    }

    @Test
    void updateSchedule_updatesExisting() throws Exception {
        ScheduleForm form = new ScheduleForm();
        form.setClassroomId(2L);
        form.setDayOfWeek(3);
        form.setStartTime("14:00");
        form.setEndTime("16:00");

        ScheduleDto dto = new ScheduleDto();
        dto.setId(2L);
        dto.setDayOfWeek(3);
        dto.setStartTime(LocalTime.of(14, 0));
        dto.setEndTime(LocalTime.of(16, 0));
        dto.setClassroom(new ClassroomDto(2L, "Laboratorij B305", "B305"));

        given(scheduleService.updateSchedule(eq(2L), any(ScheduleForm.class))).willReturn(dto);

        mockMvc.perform(put("/api/schedules/{scheduleId}", 2L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(form)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(2L))
                .andExpect(jsonPath("$.dayOfWeek").value(3))
                .andExpect(jsonPath("$.classroom.abbreviation").value("B305"));
    }

    @Test
    void deleteSchedule_deletesSuccessfully() throws Exception {
        willDoNothing().given(scheduleService).deleteSchedule(3L);

        mockMvc.perform(delete("/api/schedules/{scheduleId}", 3L))
                .andExpect(status().isNoContent());
    }
}

