package j2ee.project.controller;

import j2ee.project.models.Category;
import j2ee.project.models.Product;
import j2ee.project.models.User;
import j2ee.project.repository.CategoryRepository;
import j2ee.project.service.CategoryService;
import j2ee.project.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/category/")
public class CategoryController extends Controller{
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ProductService productService;
    @PostMapping("/add")
    @ResponseBody
    public ResponseEntity<String> register(@RequestBody Category category) {
        try {
            categoryService.addCategory(category);
            return successResponse("Add successfully", null);

        } catch (Exception e) {
            return errorResponse(e.getMessage());
        }
    }
    @GetMapping("/getAll")
    @ResponseBody
    public ResponseEntity<List<Category>> getAllCategory() {
        try {
            List<Category> categories = categoryService.getAll();
            return ResponseEntity.ok(categories);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    @DeleteMapping("/delete/{id}")
    @ResponseBody
    public ResponseEntity<String> deleteCategory(@PathVariable int id) {
        try {
            categoryService.deleteCategoryAndProducts(id);
            return successResponse("Delete successfully", null);
        } catch (Exception e) {
            return errorResponse(e.getMessage());
        }
    }
}
