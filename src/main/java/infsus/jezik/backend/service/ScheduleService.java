package infsus.jezik.backend.service;

import infsus.jezik.backend.model.dto.ScheduleDto;
import infsus.jezik.backend.model.dto.ScheduleForm;

public interface ScheduleService {
    ScheduleDto createSchedule(Long courseId, ScheduleForm form);
    ScheduleDto updateSchedule(Long scheduleId, ScheduleForm form);
    void deleteSchedule(Long scheduleId);
}
