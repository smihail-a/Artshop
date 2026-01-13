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
    private Users user;
    private Categories category;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

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

        review1 = new Reviews(user, artwork1, 5, "Amazing artwork!");
        review1.setId(1L);

        artwork2 = new Artwork(
                "Night",
                "painting",
                new BigDecimal("500"),
                "http://example.com/imagenight.jpg",
                user,
                null
        );
        artwork2.setId(200L);

        review2 = new Reviews(user, artwork2, 4, "I like it");
        review2.setId(2L);

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
