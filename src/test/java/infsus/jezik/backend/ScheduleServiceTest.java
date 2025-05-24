package infsus.jezik.backend;

import infsus.jezik.backend.model.db.*;
import infsus.jezik.backend.model.dto.ScheduleDto;
import infsus.jezik.backend.model.dto.ScheduleForm;
import infsus.jezik.backend.mapper.ScheduleMapper;
import infsus.jezik.backend.repository.*;
import infsus.jezik.backend.service.impl.ScheduleServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalTime;
import java.util.Optional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.BDDMockito.*;


@ExtendWith(MockitoExtension.class)
class ScheduleServiceTest {

    @Mock private ScheduleRepository scheduleRepository;
    @Mock private CourseRepository courseRepository;
    @Mock private ClassroomRepository classroomRepository;
    @Mock private ScheduleMapper scheduleMapper;

    @InjectMocks
    private ScheduleServiceImpl scheduleService;

    private Course course;
    private Professor professor;
    private Classroom classroom;
    private Schedule schedule;
    private ScheduleDto scheduleDto;
    private ScheduleForm form;

    @BeforeEach
    void setUp() {
        professor = new Professor();
        professor.setId(1L);

        course = new Course();
        course.setId(10L);
        course.setProfessor(professor);

        classroom = new Classroom(5L, "A101", "A101");

        schedule = new Schedule();
        schedule.setId(100L);
        schedule.setCourse(course);
        schedule.setClassroom(classroom);
        schedule.setDayOfWeek(1);
        schedule.setStartTime(LocalTime.of(10, 0));
        schedule.setEndTime(LocalTime.of(12, 0));

        scheduleDto = new ScheduleDto();
        scheduleDto.setId(100L);
        scheduleDto.setDayOfWeek(1);

        form = new ScheduleForm();
        form.setClassroomId(5L);
        form.setDayOfWeek(1);
        form.setStartTime("14:00");
        form.setEndTime("16:00");
    }

    @Test
    void createSchedule_success() {
        given(courseRepository.findById(10L)).willReturn(Optional.of(course));
        given(classroomRepository.findById(5L)).willReturn(Optional.of(classroom));
        given(scheduleRepository.findAll()).willReturn(List.of(schedule));
        given(scheduleRepository.save(any())).willReturn(schedule);
        given(scheduleMapper.toDto(schedule)).willReturn(scheduleDto);

        ScheduleDto result = scheduleService.createSchedule(10L, form);

        assertEquals(1, result.getDayOfWeek());
        verify(scheduleRepository).save(any());
    }

    @Test
    void createSchedule_overlappingClassroom_shouldThrow() {
        Schedule conflict = new Schedule();
        conflict.setClassroom(classroom);
        conflict.setDayOfWeek(1);
        conflict.setStartTime(LocalTime.of(13, 0));
        conflict.setEndTime(LocalTime.of(15, 0));

        given(courseRepository.findById(10L)).willReturn(Optional.of(course));
        given(classroomRepository.findById(5L)).willReturn(Optional.of(classroom));
        given(scheduleRepository.findAll()).willReturn(List.of(conflict));

        assertThrows(RuntimeException.class, () -> scheduleService.createSchedule(10L, form));
    }

    @Test
    void createSchedule_overlappingProfessor_shouldThrow() {
        Schedule conflict = new Schedule();
        conflict.setClassroom(new Classroom(99L, "Other", "O1"));
        conflict.setCourse(course);
        conflict.setDayOfWeek(1);
        conflict.setStartTime(LocalTime.of(13, 0));
        conflict.setEndTime(LocalTime.of(15, 0));

        given(courseRepository.findById(10L)).willReturn(Optional.of(course));
        given(classroomRepository.findById(5L)).willReturn(Optional.of(classroom));
        given(scheduleRepository.findAll()).willReturn(List.of(conflict));

        assertThrows(RuntimeException.class, () -> scheduleService.createSchedule(10L, form));
    }

    @Test
    void createSchedule_endBeforeStart_shouldThrow() {
        form.setStartTime("16:00");
        form.setEndTime("15:00");

        given(courseRepository.findById(10L)).willReturn(Optional.of(course));
        given(classroomRepository.findById(5L)).willReturn(Optional.of(classroom));

        assertThrows(RuntimeException.class, () -> scheduleService.createSchedule(10L, form));
    }

    @Test
    void updateSchedule_success() {
        given(scheduleRepository.findById(100L)).willReturn(Optional.of(schedule));
        given(classroomRepository.findById(5L)).willReturn(Optional.of(classroom));
        given(scheduleRepository.findAll()).willReturn(List.of(schedule)); // skip self
        given(scheduleRepository.save(schedule)).willReturn(schedule);
        given(scheduleMapper.toDto(schedule)).willReturn(scheduleDto);

        ScheduleDto result = scheduleService.updateSchedule(100L, form);

        assertEquals(1, result.getDayOfWeek());
        verify(scheduleRepository).save(schedule);
    }

    @Test
    void updateSchedule_conflict_shouldThrow() {
        Schedule other = new Schedule();
        other.setId(999L);
        other.setCourse(course);
        other.setClassroom(classroom);
        other.setDayOfWeek(1);
        other.setStartTime(LocalTime.of(13, 0));
        other.setEndTime(LocalTime.of(15, 0));

        given(scheduleRepository.findById(100L)).willReturn(Optional.of(schedule));
        given(classroomRepository.findById(5L)).willReturn(Optional.of(classroom));
        given(scheduleRepository.findAll()).willReturn(List.of(other));

        assertThrows(RuntimeException.class, () -> scheduleService.updateSchedule(100L, form));
    }

    @Test
    void deleteSchedule_success() {
        given(scheduleRepository.findById(100L)).willReturn(Optional.of(schedule));

        scheduleService.deleteSchedule(100L);

        verify(scheduleRepository).delete(schedule);
    }

    @Test
    void deleteSchedule_notFound_shouldThrow() {
        given(scheduleRepository.findById(999L)).willReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> scheduleService.deleteSchedule(999L));
    }
}

