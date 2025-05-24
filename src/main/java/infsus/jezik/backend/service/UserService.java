package infsus.jezik.backend.service;

import infsus.jezik.backend.model.dto.ProfessorDto;
import infsus.jezik.backend.model.dto.ProfessorListItemDto;
import infsus.jezik.backend.model.dto.StudentDto;

import java.util.List;

public interface UserService {
    List<ProfessorListItemDto> getAllProfessors();
    List<StudentDto> getAllStudents();
}
