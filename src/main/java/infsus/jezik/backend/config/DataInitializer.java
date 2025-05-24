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

        // -- PROFESSORS --
        Professor prof1 = new Professor();
        prof1.setFirstName("Maja");
        prof1.setLastName("Matetić");
        prof1.setEmail("maja.matetic@fer.hr");
        prof1.setPasswordHash("hashed123");
        prof1.setUserStatus(UserStatus.ACTIVE);
        prof1.setRegistrationDate(LocalDateTime.now());
        prof1.setBio("Specijalistica za umjetnu inteligenciju i strojno učenje.");
        professorRepository.save(prof1);

        Professor prof2 = new Professor();
        prof2.setFirstName("Ivan");
        prof2.setLastName("Kovačić");
        prof2.setEmail("ivan.kovacic@fer.hr");
        prof2.setPasswordHash("hashed123");
        prof2.setUserStatus(UserStatus.ACTIVE);
        prof2.setRegistrationDate(LocalDateTime.now());
        prof2.setBio("Stručnjak za računalne mreže i sigurnost.");
        professorRepository.save(prof2);

        // -- STUDENTS --
        Student stu1 = new Student();
        stu1.setFirstName("Ana");
        stu1.setLastName("Perić");
        stu1.setEmail("ana.peric@student.fer.hr");
        stu1.setPasswordHash("hashedStudent");
        stu1.setUserStatus(UserStatus.ACTIVE);
        stu1.setRegistrationDate(LocalDateTime.now());
        stu1.setAdditionalContact("ana.peric@outlook.com");
        studentRepository.save(stu1);

        Student stu2 = new Student();
        stu2.setFirstName("Marko");
        stu2.setLastName("Horvat");
        stu2.setEmail("marko.horvat@student.fer.hr");
        stu2.setPasswordHash("hashedStudent");
        stu2.setUserStatus(UserStatus.ACTIVE);
        stu2.setRegistrationDate(LocalDateTime.now());
        stu2.setAdditionalContact("marko.horvat@gmail.com");
        studentRepository.save(stu2);

        // -- CLASSROOMS --
        Classroom class1 = new Classroom();
        class1.setName("Dvorana A101");
        class1.setAbbreviation("A101");
        classroomRepository.save(class1);

        Classroom class2 = new Classroom();
        class2.setName("Laboratorij B305");
        class2.setAbbreviation("B305");
        classroomRepository.save(class2);

        // -- COURSES --
        Course course1 = new Course();
        course1.setName("Uvod u umjetnu inteligenciju");
        course1.setDescription("Osnove AI: pretraga, reprezentacija znanja, učenje.");
        course1.setPrice(BigDecimal.ZERO);
        course1.setProfessor(prof1);
        courseRepository.save(course1);

        Course course2 = new Course();
        course2.setName("Sigurnost računalnih mreža");
        course2.setDescription("Kriptografija, sigurnosni protokoli, analiza ranjivosti.");
        course2.setPrice(BigDecimal.ZERO);
        course2.setProfessor(prof2);
        courseRepository.save(course2);

        // -- SCHEDULES --
        Schedule sched1 = new Schedule();
        sched1.setCourse(course1);
        sched1.setClassroom(class1);
        sched1.setDayOfWeek(2);
        sched1.setStartTime(LocalTime.of(10, 0));
        sched1.setEndTime(LocalTime.of(12, 0));
        scheduleRepository.save(sched1);

        Schedule sched2 = new Schedule();
        sched2.setCourse(course2);
        sched2.setClassroom(class2);
        sched2.setDayOfWeek(4);
        sched2.setStartTime(LocalTime.of(14, 0));
        sched2.setEndTime(LocalTime.of(16, 0));
        scheduleRepository.save(sched2);

        // -- ENROLLMENTS --
        Enrollment en1 = new Enrollment();
        en1.setStudent(stu1);
        en1.setCourse(course1);
        en1.setEnrollmentDate(LocalDateTime.now());
        en1.setStatus(EnrollmentStatus.ACTIVE);
        en1.setGrade1((short) 5);
        en1.setGrade2((short) 4);
        en1.setGrade3((short) 5);
        en1.setFinalGrade((short) 5);
        enrollmentRepository.save(en1);

        Enrollment en2 = new Enrollment();
        en2.setStudent(stu2);
        en2.setCourse(course2);
        en2.setEnrollmentDate(LocalDateTime.now());
        en2.setStatus(EnrollmentStatus.ACTIVE);
        en2.setGrade1((short) 4);
        en2.setGrade2((short) 3);
        en2.setGrade3((short) 4);
        en2.setFinalGrade((short) 4);
        enrollmentRepository.save(en2);
    }
}
