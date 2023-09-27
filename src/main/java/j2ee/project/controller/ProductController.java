package j2ee.project.controller;

import j2ee.project.models.Product;
import j2ee.project.models.User;
import j2ee.project.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product/")
public class ProductController extends Controller{
    @Autowired
    private ProductService productService;
    @PostMapping("/add")
    @ResponseBody
    public ResponseEntity<String> addProduct(@RequestBody Product product) {
        try {
            productService.addProduct(product);
            return successResponse("Add successfully", null);
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
}
