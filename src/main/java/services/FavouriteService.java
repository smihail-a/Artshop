package services;
import entity.Favourites;
import org.springframework.stereotype.Service;
import repository.FavouriteRepository;

import java.util.List;

@Service
public class FavouriteService {

    private final FavouriteRepository favouriteRepository;

    public FavouriteService(FavouriteRepository favouriteRepository) {
        this.favouriteRepository = favouriteRepository;
    }

    public Favourites addToFavourites(Favourites favourite) {
        boolean exists = favouriteRepository
                .existsByUserIdAndArtworkId(
                        favourite.getUser().getId(),
                        favourite.getArtwork().getId()
                );

        if (exists) {
            throw new IllegalArgumentException("Artwork already in favourites");
        }

        return favouriteRepository.save(favourite);
    }

    public List<Favourites> getFavouritesByUser(Long userId) {
        return favouriteRepository.findByUserId(userId);
    }

    public void removeFavourite(Long favouriteId) {
        if (!favouriteRepository.existsById(favouriteId)) {
            throw new IllegalArgumentException("Favourite not found");
        }
        favouriteRepository.deleteById(favouriteId);
    }
}
