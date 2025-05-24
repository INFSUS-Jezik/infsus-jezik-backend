package infsus.jezik.backend.config;

import infsus.jezik.backend.model.db.*;
import infsus.jezik.backend.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;

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
        professor.setFirstName("Marko");
        professor.setLastName("Curkovic");
        professor.setEmail("marko.curkovic@fer.hr");
        professor.setPasswordHash("pass");
        professor.setUserStatus(UserStatus.ACTIVE);
        professor.setRegistrationDate(LocalDateTime.now());
        professor.setBio("Professor of English.");
        professor = professorRepository.save(professor);

        Professor professor2 = new Professor();
        professor2.setFirstName("Jakov");
        professor2.setLastName("Vinozganic");
        professor2.setEmail("jakov.vinozganic@fer.hr");
        professor2.setPasswordHash("pass3");
        professor2.setUserStatus(UserStatus.ACTIVE);
        professor2.setRegistrationDate(LocalDateTime.now());
        professor2.setBio("Professor of French.");
        professor2 = professorRepository.save(professor2);

        // Student
        Student student = new Student();
        student.setFirstName("Josip");
        student.setLastName("Brkan");
        student.setEmail("josip.brkan@gmail.com");
        student.setPasswordHash("pass2");
        student.setUserStatus(UserStatus.ACTIVE);
        student.setRegistrationDate(LocalDateTime.now());
        student.setAdditionalContact("123-456-7890");
        student = studentRepository.save(student);

        Student student2 = new Student();
        student2.setFirstName("Marija");
        student2.setLastName("Horvat");
        student2.setEmail("marija.horvat@gmail.com");
        student2.setPasswordHash("pass3");
        student2.setUserStatus(UserStatus.ACTIVE);
        student2.setRegistrationDate(LocalDateTime.now());
        student2.setAdditionalContact("123-446-7830");
        student2 = studentRepository.save(student2);

        // Classroom
        Classroom classroom = new Classroom();
        classroom.setName("Room 101");
        classroom.setAbbreviation("R101");
        classroomRepository.save(classroom);

        Classroom classroom2 = new Classroom();
        classroom2.setName("Room 102");
        classroom2.setAbbreviation("R102");
        classroomRepository.save(classroom2);

        // Course
        Course course = new Course();
        course.setName("English");
        course.setDescription("Introductory English course.");
        course.setPrice(new BigDecimal("100.00"));
        course.setProfessor(professor);
        courseRepository.save(course);

        Course course2 = new Course();
        course2.setName("French");
        course2.setDescription("Introductory French course.");
        course2.setPrice(new BigDecimal("105.00"));
        course2.setProfessor(professor2);
        courseRepository.save(course2);

        // Schedule
        Schedule schedule = new Schedule();
        schedule.setCourse(course);
        schedule.setClassroom(classroom);
        schedule.setDayOfWeek(1);
        schedule.setStartTime(LocalTime.of(9, 0));
        schedule.setEndTime(LocalTime.of(11, 0));
        scheduleRepository.save(schedule);

        Schedule schedule2 = new Schedule();
        schedule2.setCourse(course2);
        schedule2.setClassroom(classroom2);
        schedule2.setDayOfWeek(4);
        schedule2.setStartTime(LocalTime.of(10, 0));
        schedule2.setEndTime(LocalTime.of(12, 0));
        scheduleRepository.save(schedule2);

        // Enrollment
        Enrollment enrollment = new Enrollment();
        enrollment.setStudent(student);
        enrollment.setCourse(course);
        enrollment.setEnrollmentDate(OffsetDateTime.now());
        enrollment.setStatus(EnrollmentStatus.ACTIVE);
        enrollment.setGrade1((short) 5);
        enrollment.setGrade2((short) 4);
        enrollment.setGrade3((short) 5);
        enrollment.setFinalGrade((short) 5);
        enrollmentRepository.save(enrollment);

        Enrollment enrollment2 = new Enrollment();
        enrollment2.setStudent(student2);
        enrollment2.setCourse(course2);
        enrollment2.setEnrollmentDate(OffsetDateTime.now());
        enrollment2.setStatus(EnrollmentStatus.ACTIVE);
        enrollment2.setGrade1((short) 3);
        enrollment2.setGrade2((short) 4);
        enrollment2.setGrade3((short) 3);
        enrollment2.setFinalGrade((short) 4);
        enrollmentRepository.save(enrollment2);
    }
}
