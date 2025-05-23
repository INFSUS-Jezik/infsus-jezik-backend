package infsus.jezik.backend.repository;


import infsus.jezik.backend.model.db.Professor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfessorRepository extends JpaRepository<Professor, Long> {
}