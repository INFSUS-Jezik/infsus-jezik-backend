package infsus.jezik.backend.repository;

import infsus.jezik.backend.model.db.Classroom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClassroomRepository extends JpaRepository<Classroom, Long> {
    List<Classroom> findByNameContainingIgnoreCase(String name);
    boolean existsByAbbreviation(String abbreviation);
}
