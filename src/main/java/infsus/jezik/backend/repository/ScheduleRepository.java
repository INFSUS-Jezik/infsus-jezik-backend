package infsus.jezik.backend.repository;

import infsus.jezik.backend.model.db.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
}
