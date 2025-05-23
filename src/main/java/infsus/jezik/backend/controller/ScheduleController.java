package infsus.jezik.backend.controller;

import infsus.jezik.backend.model.dto.ScheduleDto;
import infsus.jezik.backend.model.dto.ScheduleForm;
import infsus.jezik.backend.service.ScheduleService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;

    //validacija jel postoji vec taj termin u toj ucionici??
    //OK
    @PostMapping("/api/courses/{courseId}/schedules")
    public ScheduleDto createSchedule(@PathVariable Long courseId, @RequestBody ScheduleForm form) {
        return scheduleService.createSchedule(courseId, form);
    }

    //OK
    @PutMapping("/api/schedules/{scheduleId}")
    public ScheduleDto updateSchedule(@PathVariable Long scheduleId, @RequestBody ScheduleForm form) {
        return scheduleService.updateSchedule(scheduleId, form);
    }
    //OK
    @DeleteMapping("/api/schedules/{scheduleId}")
    public ResponseEntity<Void> deleteSchedule(@PathVariable Long scheduleId) {
        scheduleService.deleteSchedule(scheduleId);
        return ResponseEntity.noContent().build();
    }
}
