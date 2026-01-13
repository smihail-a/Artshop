package repository;

import entity.Artwork;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import org.springframework.stereotype.Repository;
@Repository
public interface ArtworkRepository extends JpaRepository<Artwork, Long> {
    List<Artwork> findByCategoriesId(Long categoriesId);
    List<Artwork> findByArtistId(Long artistId);
    List<Artwork> findByTitleContainingIgnoreCase(String title);
}