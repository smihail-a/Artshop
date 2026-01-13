package controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import entity.Artwork;
import entity.Reviews;
import entity.Role;
import entity.Users;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import services.ReviewService;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class ReviewControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ReviewService reviewService;

    @InjectMocks
    private ReviewController reviewController;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(reviewController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void testCreateReview() throws Exception {
        Users user = new Users("john_doe", "password123", Role.USER, "john@example.com");
        user.setId(1L);

        Artwork artwork = new Artwork(
                "Starry Night",
                "Famous painting",
                new BigDecimal("1000"),
                "http://example.com/image.jpg",
                user,
                null
        );
        artwork.setId(100L);

        Reviews review = new Reviews(user, artwork, 5, "Amazing artwork!");
        review.setId(1L);

        when(reviewService.createReview(any(Reviews.class))).thenReturn(review);

        mockMvc.perform(post("/api/reviews")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(review)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.user.id").value(1))
                .andExpect(jsonPath("$.artwork.id").value(100))
                .andExpect(jsonPath("$.rating").value(5))
                .andExpect(jsonPath("$.comment").value("Amazing artwork!"));

        verify(reviewService, times(1)).createReview(any(Reviews.class));
    }

    @Test
    void testGetReviewsByArtwork() throws Exception {
        Users user = new Users("john_doe", "password123", Role.USER, "john@example.com");
        user.setId(1L);

        Artwork artwork = new Artwork(
                "Starry Night",
                "Famous painting",
                new BigDecimal("1000"),
                "http://example.com/image.jpg",
                user,
                null
        );
        artwork.setId(100L);

        Reviews review = new Reviews(user, artwork, 5, "Amazing artwork!");
        review.setId(1L);

        when(reviewService.getReviewsByArtwork(100L)).thenReturn(List.of(review));

        mockMvc.perform(get("/api/reviews/artwork/100"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].user.id").value(1))
                .andExpect(jsonPath("$[0].artwork.id").value(100))
                .andExpect(jsonPath("$[0].rating").value(5))
                .andExpect(jsonPath("$[0].comment").value("Amazing artwork!"));

        verify(reviewService, times(1)).getReviewsByArtwork(100L);
    }
}
