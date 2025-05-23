package infsus.jezik.backend.service;

import infsus.jezik.backend.model.dto.ProfessorDto;
import infsus.jezik.backend.model.dto.StudentDto;

import java.util.List;

public interface UserService {
    List<ProfessorDto> getAllProfessors();
    List<StudentDto> getAllStudents();
}
