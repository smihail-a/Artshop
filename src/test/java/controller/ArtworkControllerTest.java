package controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import entity.Artwork;
import entity.Categories;
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
import services.ArtworkService;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


class ArtworkControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ArtworkService artworkService;

    @InjectMocks
    private ArtworkController artworkController;

    private ObjectMapper objectMapper;
    private Users artist;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(artworkController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void testCreateArtwork() throws Exception {
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
        artwork.setId(10L);

        when(artworkService.createArtwork(any(Artwork.class))).thenReturn(artwork);

        mockMvc.perform(post("/api/artworks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(artwork)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Starry Night"));


        verify(artworkService, times(1)).createArtwork(any(Artwork.class));
    }

    @Test
    void testGetAllArtworks() throws Exception {
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
        artwork.setId(10L);


        when(artworkService.getAllArtworks()).thenReturn(List.of(artwork));

        mockMvc.perform(get("/api/artworks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].title").value("Mona Lisa"));

        verify(artworkService, times(1)).getAllArtworks();
    }

    @Test
    void testGetArtworkById() throws Exception {
        Users user = new Users("john_doe", "password123", Role.USER, "john@example.com");
        user.setId(1L);
        Artwork artwork = new Artwork(
                "",
                "Guernica",
                new BigDecimal("1000"),
                "http://example.com/image.jpg",
                user,
                null
        );
        artwork.setId(1L);

        when(artworkService.getArtworkById(1L)).thenReturn(artwork);

        mockMvc.perform(get("/api/artworks/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Guernica"));

        verify(artworkService, times(1)).getArtworkById(1L);
    }

    @Test
    void testSearchByTitle() throws Exception {
        Users user = new Users("john_doe", "password123", Role.USER, "john@example.com");
        user.setId(1L);
        Artwork artwork = new Artwork(
                "Sunflowers",
                "Famous painting",
                new BigDecimal("1000"),
                "http://example.com/image.jpg",
                user,
                null
        );
        artwork.setId(1L);

        when(artworkService.searchByTitle("Sun"))
                .thenReturn(List.of(artwork));

        mockMvc.perform(get("/api/artworks/search")
                        .param("title", "Sun"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].title").value("Sunflowers"));

        verify(artworkService, times(1)).searchByTitle("Sun");
    }

    @Test
    void testGetByCategory() throws Exception {
        Users user = new Users("john_doe", "password123", Role.USER, "john@example.com");
        user.setId(1L);
        Categories category = new Categories("Abstract");
        category.setId(1L);
        Artwork artwork = new Artwork(
                "Starry Night",
                "Famous painting",
                new BigDecimal("1000"),
                "http://example.com/image.jpg",
                user,
                category
        );
        artwork.setId(10L);

        when(artworkService.getByCategory(10L))
                .thenReturn(List.of(artwork));

        mockMvc.perform(get("/api/artworks/category/10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].title").value("Abstract"));

        verify(artworkService, times(1)).getByCategory(10L);
    }

    @Test
    void testDeleteArtwork() throws Exception {
        doNothing().when(artworkService).deleteArtwork(1L);

        mockMvc.perform(delete("/api/artworks/1"))
                .andExpect(status().isOk());

        verify(artworkService, times(1)).deleteArtwork(1L);
    }
}
