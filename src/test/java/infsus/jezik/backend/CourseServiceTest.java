package infsus.jezik.backend;

import infsus.jezik.backend.model.db.Course;
import infsus.jezik.backend.model.db.Professor;
import infsus.jezik.backend.model.dto.CourseDto;
import infsus.jezik.backend.model.dto.CourseForm;
import infsus.jezik.backend.repository.CourseRepository;
import infsus.jezik.backend.repository.ProfessorRepository;
import infsus.jezik.backend.service.impl.CourseServiceImpl;
import infsus.jezik.backend.mapper.CourseMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.BDDMockito.*;


@ExtendWith(MockitoExtension.class)
class CourseServiceTest {

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private ProfessorRepository professorRepository;

    @Mock
    private CourseMapper courseMapper;

    @InjectMocks
    private CourseServiceImpl courseService;

    private Course course;
    private CourseDto courseDto;
    private CourseForm form;
    private Professor professor;

    @BeforeEach
    void setUp() {
        professor = new Professor();
        professor.setId(1L);
        professor.setFirstName("Maja");
        professor.setLastName("Matetić");

        course = new Course();
        course.setId(1L);
        course.setName("Test Course");
        course.setDescription("Test Description");
        course.setPrice(new BigDecimal("200.00"));
        course.setProfessor(professor);
        course.setEnrollments(Set.of());
        course.setSchedules(Set.of());

        courseDto = new CourseDto();
        courseDto.setId(1L);
        courseDto.setName("Test Course");
        courseDto.setDescription("Test Description");
        courseDto.setPrice(new BigDecimal("200.00"));

        form = new CourseForm();
        form.setName("Test Course");
        form.setDescription("Test Description");
        form.setPrice(new BigDecimal("200.00"));
        form.setProfessorId(1L);
    }

    @Test
    void getCourses_shouldReturnAll() {
        given(courseRepository.findAll()).willReturn(List.of(course));
        given(courseMapper.toDto(course)).willReturn(courseDto);

        List<CourseDto> result = courseService.getCourses(null);

        assertEquals(1, result.size());
        assertEquals("Test Course", result.get(0).getName());
        verify(courseRepository).findAll();
    }

    @Test
    void getCourses_withFilter_shouldCallFilteredRepo() {
        given(courseRepository.findByNameContainingIgnoreCase("AI")).willReturn(List.of(course));
        given(courseMapper.toDto(course)).willReturn(courseDto);

        List<CourseDto> result = courseService.getCourses("AI");

        assertEquals(1, result.size());
        verify(courseRepository).findByNameContainingIgnoreCase("AI");
    }

    @Test
    void getCourseById_found() {
        given(courseRepository.findById(1L)).willReturn(Optional.of(course));
        given(courseMapper.toDto(course)).willReturn(courseDto);

        CourseDto result = courseService.getCourseById(1L);

        assertEquals("Test Course", result.getName());
        verify(courseRepository).findById(1L);
    }

    @Test
    void getCourseById_notFound_shouldThrow() {
        given(courseRepository.findById(1L)).willReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> courseService.getCourseById(1L));
    }

    @Test
    void createCourse_shouldSaveAndReturnDto() {
        given(professorRepository.findById(1L)).willReturn(Optional.of(professor));
        given(courseRepository.save(any(Course.class))).willReturn(course);
        given(courseMapper.toDto(course)).willReturn(courseDto);

        CourseDto result = courseService.createCourse(form);

        assertEquals("Test Course", result.getName());
        verify(courseRepository).save(any(Course.class));
    }

    @Test
    void createCourse_professorNotFound_shouldThrow() {
        given(professorRepository.findById(1L)).willReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> courseService.createCourse(form));
    }

    @Test
    void updateCourse_shouldUpdateFields() {
        given(courseRepository.findById(1L)).willReturn(Optional.of(course));
        given(professorRepository.findById(1L)).willReturn(Optional.of(professor));
        given(courseRepository.save(course)).willReturn(course);
        given(courseMapper.toDto(course)).willReturn(courseDto);

        CourseDto result = courseService.updateCourse(1L, form);

        assertEquals("Test Course", result.getName());
        verify(courseRepository).save(course);
    }

    @Test
    void updateCourse_notFound_shouldThrow() {
        given(courseRepository.findById(1L)).willReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> courseService.updateCourse(1L, form));
    }

    @Test
    void deleteCourse_found_shouldDelete() {
        given(courseRepository.findById(1L)).willReturn(Optional.of(course));

        courseService.deleteCourse(1L);

        verify(courseRepository).delete(course);
    }

    @Test
    void deleteCourse_notFound_shouldThrow() {
        given(courseRepository.findById(1L)).willReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> courseService.deleteCourse(1L));
    }
}

