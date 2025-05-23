package infsus.jezik.backend.service.impl;

import infsus.jezik.backend.mapper.CourseMapper;
import infsus.jezik.backend.model.db.Course;
import infsus.jezik.backend.model.db.Professor;
import infsus.jezik.backend.model.dto.CourseDto;
import infsus.jezik.backend.model.dto.CourseForm;
import infsus.jezik.backend.repository.CourseRepository;
import infsus.jezik.backend.repository.ProfessorRepository;
import infsus.jezik.backend.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final ProfessorRepository professorRepository;
    private final CourseMapper courseMapper;

    @Override
    public List<CourseDto> getCourses(String name) {
        List<Course> courses = (name != null && !name.isBlank())
                ? courseRepository.findByNameContainingIgnoreCase(name)
                : courseRepository.findAll();

        List<CourseDto> courseDtos = new ArrayList<>();
        for (Course course : courses) {
            CourseDto dto = courseMapper.toDto(course);
            courseDtos.add(dto);
        }
        return courseDtos;
    }

    @Override
    public CourseDto getCourseById(Long courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found with id: " + courseId));
        return courseMapper.toDto(course);
    }

    @Override
    public CourseDto createCourse(CourseForm form) {
        Professor professor = professorRepository.findById(form.getProfessorId())
                .orElseThrow(() -> new RuntimeException("Professor not found with id: " + form.getProfessorId()));

        Course course = new Course();
        course.setName(form.getName());
        course.setDescription(form.getDescription());
        course.setPrice(form.getPrice());
        course.setProfessor(professor);
        course.setEnrollments(Set.of());
        course.setSchedules(Set.of());

        Course savedCourse = courseRepository.save(course);

        return courseMapper.toDto(savedCourse);
    }

    @Override
    public CourseDto updateCourse(Long courseId, CourseForm form) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found with id: " + courseId));

        Professor professor = professorRepository.findById(form.getProfessorId())
                .orElseThrow(() -> new RuntimeException("Professor not found with id: " + form.getProfessorId()));

        course.setName(form.getName());
        course.setDescription(form.getDescription());
        course.setPrice(form.getPrice());
        course.setProfessor(professor);

        Course updatedCourse = courseRepository.save(course);

        return courseMapper.toDto(updatedCourse);
    }

    @Override
    public void deleteCourse(Long courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found with id: " + courseId));
        courseRepository.delete(course);
    }

}

