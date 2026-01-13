package repository;
import entity.Reviews;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import org.springframework.stereotype.Repository;
@Repository
public interface ReviewRepository extends JpaRepository<Reviews, Long> {
    List<Reviews> findByArtworkId(Long artworkId);
    List<Reviews> findByUserId(Long userId);
}
