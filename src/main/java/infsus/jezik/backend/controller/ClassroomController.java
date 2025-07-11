package infsus.jezik.backend.controller;

import infsus.jezik.backend.model.dto.ClassroomDto;
import infsus.jezik.backend.model.dto.ClassroomForm;
import infsus.jezik.backend.service.ClassroomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/classrooms")
public class ClassroomController {

    private final ClassroomService classroomService;

    @GetMapping
    public List<ClassroomDto> getClassrooms(@RequestParam(required = false) String name) {
        return classroomService.getClassrooms(name);
    }

    @GetMapping("/{classroomId}")
    public ClassroomDto getClassroomById(@PathVariable Long classroomId) {
        return classroomService.getClassroomById(classroomId);
    }

    @PostMapping
    public ClassroomDto createClassroom(@RequestBody ClassroomForm form) {
        return classroomService.createClassroom(form);
    }

    @PutMapping("/{classroomId}")
    public ClassroomDto updateClassroom(@PathVariable Long classroomId, @RequestBody ClassroomForm form) {
        return classroomService.updateClassroom(classroomId, form);
    }

    @DeleteMapping("/{classroomId}")
    public ResponseEntity<Void> deleteClassroom(@PathVariable Long classroomId) {
        classroomService.deleteClassroom(classroomId);
        return ResponseEntity.noContent().build();
    }
}
