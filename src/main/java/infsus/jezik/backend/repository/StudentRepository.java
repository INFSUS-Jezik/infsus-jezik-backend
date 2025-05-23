package infsus.jezik.backend.repository;


import infsus.jezik.backend.model.db.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
}
