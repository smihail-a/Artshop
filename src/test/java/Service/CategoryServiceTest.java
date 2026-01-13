package Service;

import entity.Categories;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import repository.CategoryRepository;
import services.CategoryService;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryService categoryService;

    private Categories category1;
    private Categories category2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        category1 = new Categories();
        category1.setId(1L);
        category1.setName("Painting");

        category2 = new Categories();
        category2.setId(2L);
        category2.setName("Digital_art");
    }

    @Test
    void testCreateCategory_Success() {
        when(categoryRepository.existsByName(category1.getName())).thenReturn(false);
        when(categoryRepository.save(category1)).thenReturn(category1);

        Categories savedCategory = categoryService.createCategory(category1);

        assertNotNull(savedCategory);
        assertEquals("Painting", savedCategory.getName());
        verify(categoryRepository, times(1)).existsByName(category1.getName());
        verify(categoryRepository, times(1)).save(category1);
    }

    @Test
    void testCreateCategory_AlreadyExists() {
        when(categoryRepository.existsByName(category1.getName())).thenReturn(true);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> categoryService.createCategory(category1));

        assertEquals("Category already exists", exception.getMessage());
        verify(categoryRepository, times(1)).existsByName(category1.getName());
        verify(categoryRepository, never()).save(any());
    }

    @Test
    void testGetAllCategories() {
        when(categoryRepository.findAll()).thenReturn(Arrays.asList(category1, category2));

        List<Categories> categories = categoryService.getAllCategories();

        assertEquals(2, categories.size());
        assertEquals("Painting", categories.get(0).getName());
        assertEquals("Digital_art", categories.get(1).getName());
        verify(categoryRepository, times(1)).findAll();
    }

}
