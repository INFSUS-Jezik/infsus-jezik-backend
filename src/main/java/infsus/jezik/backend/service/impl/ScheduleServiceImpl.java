package infsus.jezik.backend.service.impl;

import infsus.jezik.backend.mapper.ScheduleMapper;
import infsus.jezik.backend.model.db.Classroom;
import infsus.jezik.backend.model.db.Course;
import infsus.jezik.backend.model.db.Schedule;
import infsus.jezik.backend.model.dto.ScheduleDto;
import infsus.jezik.backend.model.dto.ScheduleForm;
import infsus.jezik.backend.repository.ClassroomRepository;
import infsus.jezik.backend.repository.CourseRepository;
import infsus.jezik.backend.repository.ScheduleRepository;
import infsus.jezik.backend.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final CourseRepository courseRepository;
    private final ClassroomRepository classroomRepository;
    private final ScheduleMapper scheduleMapper;


    @Override
    public ScheduleDto createSchedule(Long courseId, ScheduleForm form) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));
        Classroom classroom = classroomRepository.findById(form.getClassroomId())
                .orElseThrow(() -> new RuntimeException("Classroom not found"));

        LocalTime startTime = LocalTime.parse(form.getStartTime());
        LocalTime endTime = LocalTime.parse(form.getEndTime());

        if (!endTime.isAfter(startTime)) {
            throw new RuntimeException("End time must be after start time.");
        }

        // Check for overlapping schedules in the same classroom
        List<Schedule> classroomSchedules = scheduleRepository.findAll().stream()
                .filter(s -> s.getClassroom().getId().equals(classroom.getId()) && s.getDayOfWeek().equals(form.getDayOfWeek()))
                .toList();

        for (Schedule s : classroomSchedules) {
            if (timesOverlap(startTime, endTime, s.getStartTime(), s.getEndTime())) {
                throw new RuntimeException("Schedule overlaps with another in the same classroom.");
            }
        }

        // Check for overlapping schedules for the same professor
        List<Schedule> professorSchedules = scheduleRepository.findAll().stream()
                .filter(s -> s.getCourse().getProfessor().getId().equals(course.getProfessor().getId()) && s.getDayOfWeek().equals(form.getDayOfWeek()))
                .toList();

        for (Schedule s : professorSchedules) {
            if (timesOverlap(startTime, endTime, s.getStartTime(), s.getEndTime())) {
                throw new RuntimeException("Schedule overlaps with another for the same professor.");
            }
        }

        Schedule schedule = new Schedule();
        schedule.setCourse(course);
        schedule.setClassroom(classroom);
        schedule.setDayOfWeek(form.getDayOfWeek());
        schedule.setStartTime(LocalTime.parse(form.getStartTime()));
        schedule.setEndTime(LocalTime.parse(form.getEndTime()));

        Schedule saved = scheduleRepository.save(schedule);
        return scheduleMapper.toDto(saved);
    }

    @Override
    public ScheduleDto updateSchedule(Long scheduleId, ScheduleForm form) {
        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new RuntimeException("Schedule not found"));
        Classroom classroom = classroomRepository.findById(form.getClassroomId())
                .orElseThrow(() -> new RuntimeException("Classroom not found"));


        LocalTime startTime = LocalTime.parse(form.getStartTime());
        LocalTime endTime = LocalTime.parse(form.getEndTime());

        if (!endTime.isAfter(startTime)) {
            throw new RuntimeException("End time must be after start time.");
        }

        // Check for overlapping schedules in the same classroom (excluding current)
        List<Schedule> classroomSchedules = scheduleRepository.findAll().stream()
                .filter(s -> !s.getId().equals(scheduleId)
                        && s.getClassroom().getId().equals(classroom.getId())
                        && s.getDayOfWeek().equals(form.getDayOfWeek()))
                .toList();

        for (Schedule s : classroomSchedules) {
            if (timesOverlap(startTime, endTime, s.getStartTime(), s.getEndTime())) {
                throw new RuntimeException("Schedule overlaps with another in the same classroom.");
            }
        }

        // Check for overlapping schedules for the same professor (excluding current)
        Long professorId = schedule.getCourse().getProfessor().getId();
        List<Schedule> professorSchedules = scheduleRepository.findAll().stream()
                .filter(s -> !s.getId().equals(scheduleId)
                        && s.getCourse().getProfessor().getId().equals(professorId)
                        && s.getDayOfWeek().equals(form.getDayOfWeek()))
                .toList();

        for (Schedule s : professorSchedules) {
            if (timesOverlap(startTime, endTime, s.getStartTime(), s.getEndTime())) {
                throw new RuntimeException("Schedule overlaps with another for the same professor.");
            }
        }


        schedule.setClassroom(classroom);
        schedule.setDayOfWeek(form.getDayOfWeek());
        schedule.setStartTime(LocalTime.parse(form.getStartTime()));
        schedule.setEndTime(LocalTime.parse(form.getEndTime()));

        Schedule updated = scheduleRepository.save(schedule);
        return scheduleMapper.toDto(updated);
    }

    @Override
    public void deleteSchedule(Long scheduleId) {
        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new RuntimeException("Schedule not found"));
        scheduleRepository.delete(schedule);
    }

    private boolean timesOverlap(LocalTime start1, LocalTime end1, LocalTime start2, LocalTime end2) {
        return !start1.isAfter(end2) && !start2.isAfter(end1);
    }
}
