package infsus.jezik.backend.service.impl;

import infsus.jezik.backend.mapper.ProfessorMapper;
import infsus.jezik.backend.mapper.StudentMapper;
import infsus.jezik.backend.model.dto.ProfessorDto;
import infsus.jezik.backend.model.dto.ProfessorListItemDto;
import infsus.jezik.backend.model.dto.StudentDto;
import infsus.jezik.backend.repository.ProfessorRepository;
import infsus.jezik.backend.repository.StudentRepository;
import infsus.jezik.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final ProfessorRepository professorRepository;
    private final StudentRepository studentRepository;
    private final ProfessorMapper professorMapper;
    private final StudentMapper studentMapper;

    @Override
    public List<ProfessorListItemDto> getAllProfessors() {
        return professorRepository.findAll().stream()
                .map(p -> new ProfessorListItemDto(p.getId(), p.getFirstName() + " " + p.getLastName()))
                .toList();
    }

    @Override
    public List<StudentDto> getAllStudents() {
        return studentRepository.findAll().stream()
                .map(studentMapper::toDto)
                .toList();
    }
}
