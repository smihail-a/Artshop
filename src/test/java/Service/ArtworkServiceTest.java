package Service;

import entity.Artwork;
import entity.Categories;
import entity.Role;
import entity.Users;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import repository.ArtworkRepository;
import services.ArtworkService;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ArtworkServiceTest {

    @Mock
    private ArtworkRepository artworkRepository;

    @InjectMocks
    private ArtworkService artworkService;

    private Artwork artwork1;
    private Artwork artwork2;
    private Users artist;
    private Categories category;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        artwork1 = new Artwork();
        artwork1.setId(1L);
        artwork1.setTitle("Starry Night");
        artwork1.setDescription("Starry Night");
        artist=new Users();
        artist.setId(1L);
        artist.setUsername("John");
        artist.setPassword("password1");
        artist.setEmail("email1@gmail.com");
        artist.setRole(Role.ARTIST);
        artwork1.setArtist(artist);
        artwork1.setPrice(new BigDecimal(500));
        artwork1.setImageUrl("imageurl1.com");
        category=new Categories();
        category.setId(1L);
        category.setName("Painting");
        artwork1.setCategories(category);


        artwork2 = new Artwork();
        artwork2.setId(2L);
        artwork2.setTitle("Mona Lisa");
        artist=new Users();
        artwork2.setArtist(artist);
        artwork2.setPrice(new BigDecimal(300));
        artwork2.setImageUrl("imageurl2.com");
        artwork2.setCategories(category);

    }

    @Test
    void testCreateArtwork() {
        when(artworkRepository.save(artwork1)).thenReturn(artwork1);

        Artwork savedArtwork = artworkService.createArtwork(artwork1);

        assertNotNull(savedArtwork);
        assertEquals("Starry Night", savedArtwork.getTitle());
        verify(artworkRepository, times(1)).save(artwork1);
    }

    @Test
    void testGetArtworkById_Found() {
        when(artworkRepository.findById(1L)).thenReturn(Optional.of(artwork1));

        Artwork result = artworkService.getArtworkById(1L);

        assertNotNull(result);
        assertEquals("Starry Night", result.getTitle());
        verify(artworkRepository, times(1)).findById(1L);
    }

    @Test
    void testGetArtworkById_NotFound() {
        when(artworkRepository.findById(3L)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> artworkService.getArtworkById(3L));

        assertEquals("Artwork not found", exception.getMessage());
        verify(artworkRepository, times(1)).findById(3L);
    }

    @Test
    void testGetAllArtworks() {
        when(artworkRepository.findAll()).thenReturn(Arrays.asList(artwork1, artwork2));

        List<Artwork> artworks = artworkService.getAllArtworks();

        assertEquals(2, artworks.size());
        verify(artworkRepository, times(1)).findAll();
    }

    @Test
    void testGetByCategory() {
        Long categoryId = 1L;
        when(artworkRepository.findByCategoriesId(categoryId))
                .thenReturn(Arrays.asList(artwork1));

        List<Artwork> artworks = artworkService.getByCategory(categoryId);

        assertEquals(1, artworks.size());
        assertEquals("Starry Night", artworks.get(0).getTitle());
        verify(artworkRepository, times(1)).findByCategoriesId(categoryId);
    }

    @Test
    void testSearchByTitle() {
        when(artworkRepository.findByTitleContainingIgnoreCase("starry"))
                .thenReturn(Arrays.asList(artwork1));

        List<Artwork> results = artworkService.searchByTitle("starry");

        assertEquals(1, results.size());
        assertEquals("Starry Night", results.get(0).getTitle());
        verify(artworkRepository, times(1)).findByTitleContainingIgnoreCase("starry");
    }

    @Test
    void testDeleteArtwork_Found() {
        when(artworkRepository.existsById(1L)).thenReturn(true);

        artworkService.deleteArtwork(1L);

        verify(artworkRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteArtwork_NotFound() {
        when(artworkRepository.existsById(3L)).thenReturn(false);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> artworkService.deleteArtwork(3L));

        assertEquals("Artwork not found", exception.getMessage());
        verify(artworkRepository, never()).deleteById(anyLong());
    }
}
