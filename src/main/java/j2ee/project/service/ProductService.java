package j2ee.project.service;

import j2ee.project.models.Product;
import j2ee.project.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
    public void addProduct(Product product){
        productRepository.save(product);
    }
    public List<Product> getAll() {
        return productRepository.findAll();
    }
    
    @Transactional
    public void deleteByCategoryId(int categoryId) {
        productRepository.deleteByCategoryId(categoryId);
    }

    public Optional<Product> getProductById(int productId) {
        return productRepository.findById(Integer.valueOf(productId));
    }

}
