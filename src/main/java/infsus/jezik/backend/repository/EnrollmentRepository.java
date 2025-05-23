package infsus.jezik.backend.repository;

import infsus.jezik.backend.model.db.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
}
