package controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import entity.Categories;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import services.CategoryService;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

class CategoryControllerTest {

    private MockMvc mockMvc;

    @Mock
    private CategoryService categoryService;

    @InjectMocks
    private CategoryController categoryController;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(categoryController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void testCreateCategory() throws Exception {
        Categories category = new Categories();
        category.setId(1L);
        category.setName("Painting");

        when(categoryService.createCategory(any(Categories.class)))
                .thenReturn(category);

        mockMvc.perform(post("/api/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(category)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Painting"));

        verify(categoryService, times(1))
                .createCategory(any(Categories.class));
    }

    @Test
    void testGetAllCategories() throws Exception {
        Categories c1 = new Categories();
        c1.setId(1L);
        c1.setName("Painting");

        Categories c2 = new Categories();
        c2.setId(2L);
        c2.setName("Sculpture");

        when(categoryService.getAllCategories())
                .thenReturn(List.of(c1, c2));

        mockMvc.perform(get("/api/categories"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].name").value("Painting"))
                .andExpect(jsonPath("$[1].name").value("Sculpture"));

        verify(categoryService, times(1))
                .getAllCategories();
    }
}
