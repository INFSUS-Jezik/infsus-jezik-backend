package infsus.jezik.backend.controller;

import infsus.jezik.backend.model.dto.CourseDto;
import infsus.jezik.backend.model.dto.CourseForm;
import infsus.jezik.backend.service.CourseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/courses")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;

    @GetMapping
    public List<CourseDto> getCourses(@RequestParam(required = false) String name) {
        return courseService.getCourses(name);
    }

    @GetMapping("/{courseId}")
    public CourseDto getCourseById(@PathVariable Long courseId) {
        return courseService.getCourseById(courseId);
    }

    @PostMapping
    public CourseDto createCourse(@RequestBody @Valid CourseForm form) {
        return courseService.createCourse(form);
    }

    @PutMapping("/{courseId}")
    public CourseDto updateCourse(@PathVariable Long courseId, @RequestBody @Valid CourseForm form) {
        return courseService.updateCourse(courseId, form);
    }

    @DeleteMapping("/{courseId}")
    public ResponseEntity<Void> deleteCourse(@PathVariable Long courseId) {
        courseService.deleteCourse(courseId);
        return ResponseEntity.noContent().build();
    }

}
