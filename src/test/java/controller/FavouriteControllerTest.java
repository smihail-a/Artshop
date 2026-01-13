package controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import entity.Artwork;
import entity.Favourites;
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
import services.FavouriteService;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class FavouriteControllerTest {

    private MockMvc mockMvc;

    @Mock
    private FavouriteService favouriteService;

    @InjectMocks
    private FavouriteController favouriteController;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(favouriteController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void testAddToFavourites() throws Exception {
        Users user = new Users("john_doe", "password123", Role.USER, "john@example.com");
        user.setId(1L);

        Artwork artwork = new Artwork(
                "Starry Night",
                "Famous painting",
                new BigDecimal("1000"),
                "http://example.com/image.jpg",
                user, // artist can be the same user
                null // category not required for favourite
        );
        artwork.setId(100L);

        Favourites favourite = new Favourites(user, artwork);
        favourite.setId(1L);

        when(favouriteService.addToFavourites(any(Favourites.class))).thenReturn(favourite);

        mockMvc.perform(post("/api/favourites")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(favourite)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.user.id").value(1))
                .andExpect(jsonPath("$.artwork.id").value(100));

        verify(favouriteService, times(1)).addToFavourites(any(Favourites.class));
    }

    @Test
    void testGetUserFavourites() throws Exception {
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

        Favourites favourite = new Favourites(user, artwork);
        favourite.setId(1L);

        when(favouriteService.getFavouritesByUser(1L)).thenReturn(List.of(favourite));

        mockMvc.perform(get("/api/favourites/user/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].user.id").value(1))
                .andExpect(jsonPath("$[0].artwork.id").value(100));

        verify(favouriteService, times(1)).getFavouritesByUser(1L);
    }

    @Test
    void testRemoveFromFavourites() throws Exception {
        doNothing().when(favouriteService).removeFavourite(1L);

        mockMvc.perform(delete("/api/favourites/1"))
                .andExpect(status().isOk());

        verify(favouriteService, times(1)).removeFavourite(1L);
    }
}

