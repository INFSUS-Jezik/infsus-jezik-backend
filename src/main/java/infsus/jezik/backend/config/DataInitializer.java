package infsus.jezik.backend.config;

import infsus.jezik.backend.model.db.*;
import infsus.jezik.backend.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final ProfessorRepository professorRepository;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;
    private final ClassroomRepository classroomRepository;
    private final ScheduleRepository scheduleRepository;
    private final EnrollmentRepository enrollmentRepository;

    @Override
    public void run(String... args) {
        // Professor
        Professor professor = new Professor();
        professor.setFirstName("John");
        professor.setLastName("Doe");
        professor.setEmail("john.doe@example.com");
        professor.setPasswordHash("pass");
        professor.setUserStatus(UserStatus.ACTIVE);
        professor.setRegistrationDate(LocalDateTime.now());
        professor.setBio("Expert in linguistics.");
        professor = professorRepository.save(professor);

        // Student
        Student student = new Student();
        student.setFirstName("Jane");
        student.setLastName("Smith");
        student.setEmail("jane.smith@example.com");
        student.setPasswordHash("pass");
        student.setUserStatus(UserStatus.ACTIVE);
        student.setRegistrationDate(LocalDateTime.now());
        student.setAdditionalContact("123-456-7890");
        student = studentRepository.save(student);

        // Classroom
        Classroom classroom = new Classroom();
        classroom.setName("Room 101");
        classroom.setAbbreviation("R101");
        classroomRepository.save(classroom);

        // Course
        Course course = new Course();
        course.setName("English 101");
        course.setDescription("Introductory English course.");
        course.setPrice(new BigDecimal("100.00"));
        course.setProfessor(professor);
        courseRepository.save(course);

        // Schedule
        Schedule schedule = new Schedule();
        schedule.setCourse(course);
        schedule.setClassroom(classroom);
        schedule.setDayOfWeek(1);
        schedule.setStartTime(LocalTime.of(9, 0));
        schedule.setEndTime(LocalTime.of(11, 0));
        scheduleRepository.save(schedule);

        // Enrollment
        Enrollment enrollment = new Enrollment();
        enrollment.setStudent(student);
        enrollment.setCourse(course);
        enrollment.setEnrollmentDate(LocalDateTime.now());
        enrollment.setStatus(EnrollmentStatus.ACTIVE);
        enrollment.setGrade1((short) 5);
        enrollment.setGrade2((short) 4);
        enrollment.setGrade3((short) 5);
        enrollment.setFinalGrade((short) 5);
        enrollmentRepository.save(enrollment);
    }
}
