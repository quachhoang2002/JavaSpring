package j2ee.project.service;

import j2ee.project.models.Category;
import j2ee.project.models.Manufacture;
import j2ee.project.models.Product;
import j2ee.project.repository.CategoryRepository;
import j2ee.project.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Service
public class CategoryService extends BaseService {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;


    @Autowired
    public CategoryService(CategoryRepository categoryRepository, ProductRepository productRepository) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
    }

    //add category
    public Category addCategory(Category category) {
        return categoryRepository.save(category);
    }

    //get all categories
    // READ - Get all Manufactories with pagination
    public List<Category> getAllSort(int page, long limit, String sortBy, String sortType,String name) {
        //get and sort by id
        Stream<Category> data = categoryRepository.findWithOrder(sortBy, sortType).stream();
        data = data.filter(product -> product.getName().toLowerCase().contains(name.toLowerCase()));

        return data.skip((page - 1) * limit).limit(limit).toList();
    }
    public List<Category> getAll(){
        return categoryRepository.findAll();
    }
    //get category by id
    public Optional<Category> getCategoryById(Integer id) {
        return categoryRepository.findById(id);
    }

    //update category
    public Category updateCategory(Category category) {
        Category existingCategory = categoryRepository.findById(category.getId()).orElse(null);
        existingCategory.setName(category.getName());
        existingCategory.setDescription(category.getDescription());
        return categoryRepository.save(existingCategory);
    }

    //delete category
    public String deleteCategory(Integer id) {
        categoryRepository.deleteById(id);
        return "Category removed !! " + id;
    }

    public long count(String name){
        Stream<Category> data = categoryRepository.findAll().stream();
        data = data.filter(product -> product.getName().toLowerCase().contains(name.toLowerCase()));

        return data.count();
    }

    public void deleteCategoryAndProducts(int categoryId) {
        Category category = categoryRepository.findById(categoryId).orElse(null);

        if (category == null) {
            throw new RuntimeException("Category not found");
        }

        // Lấy danh sách sản phẩm thuộc về danh mục này
        List<Product> products = productRepository.findByCategory(category);

        // Xóa danh mục
        categoryRepository.delete(category);

        // Xóa các sản phẩm thuộc danh mục này
        for (Product product : products) {
            productRepository.delete(product);
        }
    }

}
