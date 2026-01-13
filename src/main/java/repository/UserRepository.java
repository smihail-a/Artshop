package repository;
import entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import org.springframework.stereotype.Repository;
@Repository
public interface UserRepository extends JpaRepository<Users, Long> {
    Optional<Users> findByEmail(String Email);
    Optional<Users> findByUsername(String username);
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);

}