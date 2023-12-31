package j2ee.project.controller.Admin;

import j2ee.project.DTO.ProductDTO;
import j2ee.project.controller.Controller;
import j2ee.project.models.Product;
import j2ee.project.repository.ProductRepository;
import j2ee.project.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/admin/product")
public class ProductController extends Controller {

    @Autowired
    private ProductService productService;
    @Autowired
    private ProductRepository productRepository;

    @GetMapping("")
    @ResponseBody
    public ResponseEntity<String> getProducts(@RequestParam(defaultValue = "1") int page,
                                              @RequestParam(defaultValue = "8") int size,
                                              @RequestParam(defaultValue = "id") String sortBy,
                                              @RequestParam(defaultValue = "ASC") String sortType,
                                              @RequestParam(defaultValue = "") String name,
                                              @RequestParam(defaultValue = "") String category,
                                              @RequestParam(defaultValue = "") String manufacture,
                                              @RequestParam(defaultValue = "") String price
    ) {
        try {
            HashMap<String, String> filters = new HashMap<>();
            if (!name.isEmpty()) {
                filters.put("name", name);
            }

            if (!category.isEmpty()) {
                filters.put("category", category);
            }

            if (!manufacture.isEmpty()) {
                filters.put("manufacture", manufacture);
            }

            if (!price.isEmpty()) {
                filters.put("price", price);
            }

            List<Product> products = productService.getAllSort(page, size, sortBy, sortType, filters);

            long totalItems = productService.count(filters);
            Map<String, Object> metaData = buildPage(totalItems, page, size);
            return this.successResponse("Get all product successfully", products, metaData);
        } catch (Exception e) {
            return errorResponse(e.getMessage());
        }
    }


    @GetMapping("/getAll")
    public ResponseEntity<?> getAll() {
        List<Product> products = productRepository.findAll();

        if (products != null && !products.isEmpty()) {
            return successResponse("Get all product successfully", products);
        } else {
            return errorResponse("Product not found");
        }
    }


    @GetMapping("/{id}")
    public ResponseEntity<String> getProduct(@PathVariable int id) {
        Optional<Product> product = productService.getById(id);
        if (product.isPresent()) {
            return successResponse("Get product by id successfully", product.get());
        } else {
            return errorResponse("Product not found");
        }
    }


    @PostMapping("")
    public ResponseEntity<String> addProduct(
            @Valid @ModelAttribute ProductDTO productReq,
            BindingResult bindingResult
    ) {
        try {
            if (bindingResult.hasErrors()) {
                StringBuilder errorMessage = new StringBuilder("Validation errors: ");
                bindingResult.getAllErrors().forEach(error -> {
                    errorMessage.append(error.getDefaultMessage()).append("; ");
                });
                return errorResponse(errorMessage.toString());
            }

            Product data = productService.add(productReq);
            return successResponse("Add product successfully", data);
        } catch (Exception e) {
            return errorResponse(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateProduct(
            @PathVariable int id,
            @Valid @ModelAttribute ProductDTO req,
            BindingResult bindingResult
    ) {
        try {
            if (bindingResult.hasErrors()) {
                StringBuilder errorMessage = new StringBuilder("Validation errors: ");
                bindingResult.getAllErrors().forEach(error -> {
                    errorMessage.append(error.getDefaultMessage()).append("; ");
                });
                return errorResponse(errorMessage.toString());
            }

            Product data = productService.update(req, id);
            return successResponse("Update manufacture.js successfully", data);
        } catch (Exception e) {
            return errorResponse(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable int id) {
        try {
            productService.delete(id);
            return successResponse("Delete manufacture.js successfully", null);
        } catch (Exception e) {
            return errorResponse(e.getMessage());
        }
    }

}
