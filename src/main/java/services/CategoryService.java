package services;
import entity.Categories;
import org.springframework.stereotype.Service;
import repository.CategoryRepository;

import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Categories createCategory(Categories category) {
        if (categoryRepository.existsByName(category.getName())) {
            throw new IllegalArgumentException("Category already exists");
        }
        return categoryRepository.save(category);
    }

    public List<Categories> getAllCategories() {
        return categoryRepository.findAll();
    }
}

