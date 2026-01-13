package repository;
import entity.Favourites;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import org.springframework.stereotype.Repository;
@Repository
public interface FavouriteRepository extends JpaRepository<Favourites, Long> {
    List<Favourites> findByUserId(Long userId);
    boolean existsByUserIdAndArtworkId(Long userId, Long artworkId);
}