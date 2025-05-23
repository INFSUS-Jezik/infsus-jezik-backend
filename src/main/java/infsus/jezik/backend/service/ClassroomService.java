package infsus.jezik.backend.service;

import infsus.jezik.backend.model.dto.ClassroomDto;
import infsus.jezik.backend.model.dto.ClassroomForm;

import java.util.List;

public interface ClassroomService {
    List<ClassroomDto> getClassrooms(String name);
    ClassroomDto getClassroomById(Long classroomId);
    ClassroomDto createClassroom(ClassroomForm form);
    ClassroomDto updateClassroom(Long classroomId, ClassroomForm form);
    void deleteClassroom(Long classroomId);
}
