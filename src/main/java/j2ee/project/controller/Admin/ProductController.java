package j2ee.project.controller.Admin;

import j2ee.project.DTO.ProductDTO;
import j2ee.project.controller.Controller;
import j2ee.project.models.Product;
import j2ee.project.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/admin/product")
public class ProductController extends Controller {

    @Autowired
    private ProductService productService;

    @GetMapping("")
    @ResponseBody
    public ResponseEntity<String> getProducts(@RequestParam(defaultValue = "1") int page,
                                              @RequestParam(defaultValue = "10") int size,
                                              @RequestParam(defaultValue = "id") String sortBy,
                                              @RequestParam(defaultValue = "ASC") String sortType
    ) {
        try {
            List<Product> products = productService.getAll(page, size, sortBy, sortType);

            long totalItems = productService.count();
            Map<String, Object> metaData = buildPage(totalItems, page, size);
            return this.successResponse("Get all product successfully", products, metaData);
        } catch (Exception e) {
            return errorResponse(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> getProduct(@PathVariable int id) {
        Optional<Product> product = productService.getById(id);
        if (product.isPresent()) {
            return successResponse("Get manufacture.js by id successfully", product.get());
        } else {
            return errorResponse("Manufacture not found");
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
