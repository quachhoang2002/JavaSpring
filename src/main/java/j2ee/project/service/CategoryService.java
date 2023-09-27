package j2ee.project.service;

import j2ee.project.models.Category;
import j2ee.project.repository.CategoryRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;
    @Autowired
    private ProductService productService;
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }


    public void addCategory(Category category) {
        categoryRepository.save(category);
    }

    @Transactional
    public void deleteCategoryAndProducts(int categoryId) {
        productService.deleteByCategoryId(categoryId);
        categoryRepository.deleteById(categoryId);
    }

    public List<Category> getAll() {
        return categoryRepository.findAll();
    }
}
