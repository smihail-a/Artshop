package Service;

import entity.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import repository.FavouriteRepository;
import services.FavouriteService;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FavouriteServiceTest {

    @Mock
    private FavouriteRepository favouriteRepository;

    @InjectMocks
    private FavouriteService favouriteService;

    private Favourites favourite1;
    private Favourites favourite2;
    private Users user;
    private Users artist;
    private Artwork artwork1;
    private Artwork artwork2;
    private Categories category;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        user = new Users();
        user.setId(1L);
        user.setUsername("John");
        user.setPassword("123456789");
        user.setEmail("email5@yahoo.com");
        user.setRole(Role.USER);

        user = new Users("john_doe", "password123", Role.USER, "john@example.com");
        user.setId(1L);

        artwork1 = new Artwork(
                "Starry Night",
                "Famous painting",
                new BigDecimal("1000"),
                "http://example.com/image.jpg",
                user,
                null
        );
        artwork1.setId(100L);

        favourite1 = new Favourites(user, artwork1);
        favourite1.setId(1L);

        artwork2 = new Artwork(
                "Night",
                "painting",
                new BigDecimal("500"),
                "http://example.com/imagenight.jpg",
                user,
                null
        );
        artwork2.setId(100L);

        favourite2 = new Favourites(user, artwork2);
        favourite2.setId(1L);

    }

    @Test
    void testAddToFavourites_Success() {
        when(favouriteRepository.existsByUserIdAndArtworkId(user.getId(), artwork1.getId()))
                .thenReturn(false);
        when(favouriteRepository.save(favourite1)).thenReturn(favourite1);

        Favourites savedFavourite = favouriteService.addToFavourites(favourite1);

        assertNotNull(savedFavourite);
        assertEquals(favourite1.getArtwork().getId(), savedFavourite.getArtwork().getId());
        verify(favouriteRepository, times(1)).existsByUserIdAndArtworkId(user.getId(), artwork1.getId());
        verify(favouriteRepository, times(1)).save(favourite1);
    }

    @Test
    void testAddToFavourites_AlreadyExists() {
        when(favouriteRepository.existsByUserIdAndArtworkId(user.getId(), artwork1.getId()))
                .thenReturn(true);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> favouriteService.addToFavourites(favourite1));

        assertEquals("Artwork already in favourites", exception.getMessage());
        verify(favouriteRepository, times(1)).existsByUserIdAndArtworkId(user.getId(), artwork1.getId());
        verify(favouriteRepository, never()).save(any());
    }

    @Test
    void testGetFavouritesByUser() {
        when(favouriteRepository.findByUserId(user.getId()))
                .thenReturn(Arrays.asList(favourite1, favourite2));

        List<Favourites> favourites = favouriteService.getFavouritesByUser(user.getId());

        assertEquals(2, favourites.size());
        verify(favouriteRepository, times(1)).findByUserId(user.getId());
    }

    @Test
    void testRemoveFavourite_Success() {
        when(favouriteRepository.existsById(1L)).thenReturn(true);

        favouriteService.removeFavourite(1L);

        verify(favouriteRepository, times(1)).existsById(1L);
        verify(favouriteRepository, times(1)).deleteById(1L);
    }

    @Test
    void testRemoveFavourite_NotFound() {
        when(favouriteRepository.existsById(3L)).thenReturn(false);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> favouriteService.removeFavourite(3L));

        assertEquals("Favourite not found", exception.getMessage());
        verify(favouriteRepository, times(1)).existsById(3L);
        verify(favouriteRepository, never()).deleteById(anyLong());
    }
}
