package infsus.jezik.backend.service.impl;

import infsus.jezik.backend.mapper.ClassroomMapper;
import infsus.jezik.backend.model.db.Classroom;
import infsus.jezik.backend.model.dto.ClassroomDto;
import infsus.jezik.backend.model.dto.ClassroomForm;
import infsus.jezik.backend.repository.ClassroomRepository;
import infsus.jezik.backend.repository.ScheduleRepository;
import infsus.jezik.backend.service.ClassroomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class ClassroomServiceImpl implements ClassroomService {

    private final ClassroomRepository classroomRepository;
    private final ScheduleRepository scheduleRepository;
    private final ClassroomMapper classroomMapper;

    @Override
    public List<ClassroomDto> getClassrooms(String name) {
        List<Classroom> classrooms = (name != null && !name.isBlank())
                ? classroomRepository.findByNameContainingIgnoreCase(name)
                : classroomRepository.findAll();
        return classrooms.stream().map(classroomMapper::toDto).toList();
    }

    @Override
    public ClassroomDto getClassroomById(Long classroomId) {
        Classroom classroom = classroomRepository.findById(classroomId)
                .orElseThrow(() -> new RuntimeException("Classroom not found"));
        return classroomMapper.toDto(classroom);
    }

    @Override
    public ClassroomDto createClassroom(ClassroomForm form) {
        if (classroomRepository.existsByAbbreviation(form.getAbbreviation())) {
            throw new RuntimeException("Abbreviation must be unique");
        }
        Classroom classroom = new Classroom();
        classroom.setName(form.getName());
        classroom.setAbbreviation(form.getAbbreviation());
        return classroomMapper.toDto(classroomRepository.save(classroom));
    }

    @Override
    public ClassroomDto updateClassroom(Long classroomId, ClassroomForm form) {
        Classroom classroom = classroomRepository.findById(classroomId)
                .orElseThrow(() -> new RuntimeException("Classroom not found"));
        if (!classroom.getAbbreviation().equals(form.getAbbreviation()) &&
                classroomRepository.existsByAbbreviation(form.getAbbreviation())) {
            throw new RuntimeException("Abbreviation must be unique");
        }
        classroom.setName(form.getName());
        classroom.setAbbreviation(form.getAbbreviation());
        return classroomMapper.toDto(classroomRepository.save(classroom));
    }

    @Override
    public void deleteClassroom(Long classroomId) {
        Classroom classroom = classroomRepository.findById(classroomId)
                .orElseThrow(() -> new RuntimeException("Classroom not found"));
        boolean used = scheduleRepository.findAll().stream()
                .anyMatch(s -> s.getClassroom().getId().equals(classroomId));
        if (used) throw new RuntimeException("Classroom is used in a schedule");
        classroomRepository.delete(classroom);
    }
}
