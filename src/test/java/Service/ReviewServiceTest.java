package Service;

import entity.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import repository.ReviewRepository;
import services.ReviewService;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ReviewServiceTest {

    @Mock
    private ReviewRepository reviewRepository;

    @InjectMocks
    private ReviewService reviewService;

    private Reviews review1;
    private Reviews review2;
    private Artwork artwork1;
    private Artwork artwork2;
    private Users artist;
    private Categories category;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        review1 = new Reviews();
        review1.setId(1L);
        review1.setComment("Amazing artwork!");
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
        review1.setArtwork(artwork1);
        review1.setRating(5);

        review2 = new Reviews();
        review2.setId(2L);
        review2.setComment("Loved it!");
        artwork2 = new Artwork();
        artwork2.setTitle("Mona Lisa");
        artwork2.setDescription("Mona Lisa");
        artwork2.setArtist(artist);
        artwork2.setPrice(new BigDecimal(300));
        artwork2.setImageUrl("imageurl2.com");
        artwork2.setCategories(category);
        review2.setArtwork(artwork2);
        review2.setRating(4);
    }

    @Test
    void testCreateReview() {
        when(reviewRepository.save(review1)).thenReturn(review1);

        Reviews savedReview = reviewService.createReview(review1);

        assertNotNull(savedReview);
        assertEquals("Amazing artwork!", savedReview.getComment());
        verify(reviewRepository, times(1)).save(review1);
    }

    @Test
    void testGetReviewsByArtwork() {
        Long artworkId = 100L;
        when(reviewRepository.findByArtworkId(artworkId))
                .thenReturn(Arrays.asList(review1, review2));

        List<Reviews> reviews = reviewService.getReviewsByArtwork(artworkId);

        assertEquals(2, reviews.size());
        assertEquals("Amazing artwork!", reviews.get(0).getComment());
        assertEquals("Loved it!", reviews.get(1).getComment());
        verify(reviewRepository, times(1)).findByArtworkId(artworkId);
    }
}
