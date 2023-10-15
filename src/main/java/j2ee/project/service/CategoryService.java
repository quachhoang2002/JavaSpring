package j2ee.project.service;

import j2ee.project.models.Category;
import j2ee.project.models.Manufacture;
import j2ee.project.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService extends BaseService {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    //add category
    public Category addCategory(Category category) {
        return categoryRepository.save(category);
    }

    //get all categories
    // READ - Get all Manufactories with pagination
    public List<Category> getAll(int page, long limit, String sortBy, String sortType) {
        //get and sort by id
        return categoryRepository.findWithOrder(sortBy, sortType).stream().skip((page - 1) * limit).limit(limit).toList();
    }

    //get category by id
    public Optional<Category> getCategoryById(Integer id) {
        return categoryRepository.findById(id);
    }

    //update category
    public Category updateCategory(Category category) {
        Category existingCategory = categoryRepository.findById(category.getId()).orElse(null);
        existingCategory.setName(category.getName());
        return categoryRepository.save(existingCategory);
    }

    //delete category
    public String deleteCategory(Integer id) {
        categoryRepository.deleteById(id);
        return "Category removed !! " + id;
    }

    public long countAllManufacture(){
        return categoryRepository.count();
    }
}
