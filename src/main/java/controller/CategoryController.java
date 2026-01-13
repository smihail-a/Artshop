package controller;
import entity.Categories;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import services.CategoryService;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@Tag(name = "Categories", description = "Artwork categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Operation(summary = "Create category")
    @PostMapping
    public Categories create(@Valid @RequestBody Categories category) {
        return categoryService.createCategory(category);
    }

    @Operation(summary = "Get all categories")
    @GetMapping
    public List<Categories> getAll() {
        return categoryService.getAllCategories();
    }
}
