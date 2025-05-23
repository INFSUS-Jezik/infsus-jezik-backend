package infsus.jezik.backend.controller;

import infsus.jezik.backend.model.dto.ProfessorDto;
import infsus.jezik.backend.model.dto.StudentDto;
import infsus.jezik.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/api/professors/list")
    public List<ProfessorDto> getProfessorsList() {
        return userService.getAllProfessors();
    }

    @GetMapping("/api/students/list")
    public List<StudentDto> getStudentsList() {
        return userService.getAllStudents();
    }
}
