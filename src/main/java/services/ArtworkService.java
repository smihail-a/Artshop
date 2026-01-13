package services;
import org.springframework.stereotype.Service;
import entity.Artwork;
import repository.ArtworkRepository;

import java.util.List;
@Service
public class ArtworkService {
    private final ArtworkRepository artworkRepository;

    public ArtworkService(ArtworkRepository artworkRepository) {
        this.artworkRepository = artworkRepository;
    }

    public Artwork createArtwork(Artwork artwork) {
        return artworkRepository.save(artwork);
    }

    public Artwork getArtworkById(Long id) {
        return artworkRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Artwork not found"));
    }

    public List<Artwork> getAllArtworks() {
        return artworkRepository.findAll();
    }

    public List<Artwork> getByCategory(Long categoryId) {
        return artworkRepository.findByCategoriesId(categoryId);
    }

    public List<Artwork> searchByTitle(String title) {
        return artworkRepository.findByTitleContainingIgnoreCase(title);
    }
    public void deleteArtwork(Long id) {
        if (!artworkRepository.existsById(id)) {
            throw new IllegalArgumentException("Artwork not found");
        }
        artworkRepository.deleteById(id);
    }
}