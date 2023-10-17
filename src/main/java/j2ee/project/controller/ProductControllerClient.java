package j2ee.project.controller;

import j2ee.project.DTO.AddProductReq;
import j2ee.project.models.Product;
import j2ee.project.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/product/")
public class ProductControllerClient extends Controller{
    @Autowired
    private ProductService productService;
    @PostMapping("/add")
    @ResponseBody
    public ResponseEntity<String> addProduct(@RequestBody AddProductReq productReq) {
        try {
            Product product = productService.add(productReq);
            return successResponse("Add successfully", product);
        } catch (Exception e) {
            return errorResponse(e.getMessage());
        }
    }

    @GetMapping("/getAll")
    @ResponseBody
    public ResponseEntity<List<Product>> getAllProducts() {
        try {
            List<Product> products = productService.getAll();
            return ResponseEntity.ok(products);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<Product> getProductById(@PathVariable int id) {
        try {
            Optional<Product> product = productService.getProductById(id);
            if (product.isPresent()) {
                return ResponseEntity.ok(product.get());
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

}
