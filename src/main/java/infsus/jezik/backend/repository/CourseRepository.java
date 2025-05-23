package infsus.jezik.backend.repository;


import infsus.jezik.backend.model.db.Course;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long> {

    @EntityGraph(attributePaths = {"professor", "schedules", "enrollments"})
    List<Course> findByNameContainingIgnoreCase(@Param("name") String name);

    @EntityGraph(attributePaths = {"professor", "schedules", "enrollments"})
    List<Course> findAll();
}
