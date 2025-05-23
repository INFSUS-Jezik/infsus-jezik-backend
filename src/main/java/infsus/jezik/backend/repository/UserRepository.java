package infsus.jezik.backend.repository;


import infsus.jezik.backend.model.db.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
