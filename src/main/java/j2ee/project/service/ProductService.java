package j2ee.project.service;

import j2ee.project.DTO.AddProductReq;
import j2ee.project.models.Category;
import j2ee.project.models.Product;
import j2ee.project.models.Manufacture;
import j2ee.project.repository.CategoryRepository;
import j2ee.project.repository.ManufactureRepository;
import j2ee.project.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ManufactureRepository manufactureRepository;

    @Autowired
    public ProductService(ProductRepository productRepository,
                          CategoryRepository categoryRepository,
                          ManufactureRepository manufactureRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.manufactureRepository = manufactureRepository;
    }

    //add product
    public Product add(AddProductReq productReq) {
        Category category = categoryRepository.findById(productReq.getCategoryID()).orElse(null);
        Manufacture manufacture = manufactureRepository.findById(productReq.getManufactureID()).orElse(null);

        if (category == null || manufacture == null) {
            throw new RuntimeException("Category or Manufacture not found");
        }

        Product product = new Product();
        product.setName(productReq.getName());
        product.setCategory(category);
        product.setManufacture(manufacture);
        product.setPrice(productReq.getPrice());
        product.setDescription(productReq.getDescription());
        product.setImagePath(productReq.getImagePath());
        return productRepository.save(product);
    }

    //get all categories
    // READ - Get all Manufactories with pagination
    public List<Product> getAllSort(int page, long limit, String sortBy, String sortType) {
        //get and sort by id
        return productRepository.findWithOrder(sortBy, sortType).stream().skip((page - 1) * limit).limit(limit).toList();
    }
    public List<Product> getAll(){
        return productRepository.findAll();
    }
    //get product by id
    public Optional<Product> getById(Integer id) {
        return productRepository.findById(id);
    }

    //update product
    public Product update(Product product) {
        Product existingCategory = productRepository.findById(product.getId()).orElse(null);
        existingCategory.setName(product.getName());
        return productRepository.save(existingCategory);
    }

    //delete product
    public String delete(Integer id) {
        productRepository.deleteById(id);
        return "Product removed !! " + id;
    }

    public long count() {
        return productRepository.count();
    }

    public Optional<Product> getProductById(int productId) {
        return productRepository.findById(productId);
    }
}
