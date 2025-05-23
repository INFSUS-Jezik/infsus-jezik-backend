package infsus.jezik.backend.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ScheduleForm {
    private Long classroomId;
    private Integer dayOfWeek;
    private String startTime; // "HH:mm"
    private String endTime;   // "HH:mm"
}
