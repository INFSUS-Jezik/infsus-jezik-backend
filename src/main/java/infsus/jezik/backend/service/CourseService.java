package infsus.jezik.backend.service;

import infsus.jezik.backend.model.dto.CourseDto;
import infsus.jezik.backend.model.dto.CourseForm;

import java.util.List;

public interface CourseService {
    List<CourseDto> getCourses(String name);

    CourseDto getCourseById(Long courseId);

    CourseDto createCourse(CourseForm form);

    CourseDto updateCourse(Long courseId, CourseForm form);

    void deleteCourse(Long courseId);
}
