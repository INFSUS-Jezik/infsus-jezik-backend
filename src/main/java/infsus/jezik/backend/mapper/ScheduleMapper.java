package infsus.jezik.backend.mapper;

import infsus.jezik.backend.model.db.Schedule;
import infsus.jezik.backend.model.dto.ScheduleDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = ClassroomMapper.class)
public interface ScheduleMapper {
    ScheduleDto toDto(Schedule schedule);
}
