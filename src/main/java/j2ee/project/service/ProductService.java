package j2ee.project.service;

import j2ee.project.DTO.ProductDTO;
import j2ee.project.models.Category;
import j2ee.project.models.Product;
import j2ee.project.models.Manufacture;
import j2ee.project.repository.CategoryRepository;
import j2ee.project.repository.ManufactureRepository;
import j2ee.project.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

@Service
public class ProductService extends BaseService {

    private static final String IMAGE_FOLDER = "product";

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
    public Product add(ProductDTO productReq) {
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
        try {
            MultipartFile image = productReq.getImage();
            if (image != null && !image.isEmpty()) {
                String imagePath = this.buildImagePath(image, IMAGE_FOLDER);
                product.setImagePath(imagePath);
            }
        } catch (Exception e) {

        }

        return productRepository.save(product);
    }

    //get all categories
    // READ - Get all Manufactories with pagination
    public List<Product> getAllSort(int page, int limit, String sortBy, String sortType, Map<String, String> filters) {
        //get and sort by id
        Stream<Product> products = productRepository.findWithOrder(sortBy, sortType).stream();
        products = buildFilter(products, filters, page, limit);
        return products.toList();
    }

    public List<Product> getAll() {
        return productRepository.findAll();
    }

    //get product by id
    public Optional<Product> getById(Integer id) {
        return productRepository.findById(id);
    }

    //update product
    public Product update(ProductDTO product, Integer id) {
        Product existingProd = productRepository.findById(id).orElse(null);
        if (existingProd == null) {
            throw new RuntimeException("Product not found");
        }
        Category category = categoryRepository.findById(product.getCategoryID()).orElse(null);
        Manufacture manufacture = manufactureRepository.findById(product.getManufactureID()).orElse(null);
        if (category == null || manufacture == null) {
            throw new RuntimeException("Category or Manufacture not found");
        }

        existingProd.setName(product.getName());
        existingProd.setCategory(category);
        existingProd.setManufacture(manufacture);
        existingProd.setPrice(product.getPrice());
        existingProd.setDescription(product.getDescription());
        try {
            MultipartFile image = product.getImage();
            if (image != null && !image.isEmpty()) {
                String imagePath = this.buildImagePath(image, IMAGE_FOLDER);
                existingProd.setImagePath(imagePath);
            }
        } catch (Exception e) {

        }
        return productRepository.save(existingProd);
    }

    //delete product
    public String delete(Integer id) {
        productRepository.deleteById(id);
        return "Product removed !! " + id;
    }

    public long count(Map<String, String> filters) {
        Stream<Product> products = productRepository.findAll().stream();
        products = buildFilter(products, filters, 0, 0);

        return products.count();
    }

    public Stream<Product> buildFilter(Stream<Product> products, Map<String, String> filters, int page, int limit) {
        for (Map.Entry<String, String> entry : filters.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();

            if (key.equals("name")) {
                products = products.filter(product -> product.getName().toLowerCase().contains(value.toLowerCase()));
            }
            if (key.equals("category")) {
                // Parse the value string to an integer
                int categoryId = Integer.parseInt(value);
                // Use the parsed integer for filtering
                products = products.filter(product -> product.getCategory().getId() == categoryId);
            }
            if (key.equals("manufacture")) {
                int manufacture = Integer.parseInt(value);
                // Use the parsed integer for filtering
                products = products.filter(product -> product.getManufacture().getId() == manufacture);
            }

            if (key.equals("price")) {
                // Parse the value string to an integer
                int price = Integer.parseInt(value);
                // Use the parsed integer for filtering
                products = products.filter(product -> product.getPrice() <= price);
            }
        }

        if(page != 0 && limit != 0){
            products = products.skip((page - 1) * limit).limit(limit);
        }

        return products;
    }

    public Optional<Product> getProductById(int productId) {
        return productRepository.findById(productId);
    }
}
