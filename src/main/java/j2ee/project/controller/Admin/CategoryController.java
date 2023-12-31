package j2ee.project.controller.Admin;

import j2ee.project.controller.Controller;
import j2ee.project.models.Category;
import j2ee.project.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/admin/category")
public class CategoryController extends Controller {
    @Autowired
    private CategoryService categoryService;

    @GetMapping("")
    @ResponseBody
    public ResponseEntity<String> getAllManufacture(@RequestParam(defaultValue = "1") int page,
                                                    @RequestParam(defaultValue = "10") int size,
                                                    @RequestParam(defaultValue = "id") String sortBy,
                                                    @RequestParam(defaultValue = "ASC") String sortType,
                                                    @RequestParam(defaultValue = "") String name
    ) {
        try {
            List<Category> listManufacture = categoryService.getAllSort(page, size, sortBy, sortType,name);
            //foreach to set image path
            //meta data
            long totalItems = categoryService.count(name);
            Map<String, Object> metaData = buildPage(totalItems, page, size);
            return this.successResponse("Get all manufacture.js successfully", listManufacture, metaData);
        } catch (Exception e) {
            return errorResponse(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> getManufactureById(@PathVariable int id) {
        Optional<Category> category = categoryService.getCategoryById(id);
        if (category.isPresent()) {
            return successResponse("Get manufacture.js by id successfully", category.get());
        } else {
            return errorResponse("Manufacture not found");
        }
    }

    @PostMapping("")
    public ResponseEntity<String> addManufacture(@ModelAttribute Category category) {
        try {
            if (category.getName() == null || category.getName().isEmpty()) {
                return errorResponse("Name is required");
            }
            Category data = categoryService.addCategory(category);
            return successResponse("Add manufacture.js successfully", data);
        } catch (Exception e) {
            return errorResponse(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateManufacture(@PathVariable int id, @ModelAttribute Category category) {
        try {
            if (category.getName() == null || category.getName().isEmpty()){
                return errorResponse("Name is required");
            }
            category.setId(id);
            category.setName(category.getName());
            category.setDescription(category.getDescription());
            Category data = categoryService.updateCategory(category);
            return successResponse("Update manufacture.js successfully", data);
        } catch (Exception e) {
            return errorResponse(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteManufacture(@PathVariable int id) {
        try {
            categoryService.deleteCategory(id);
            return successResponse("Delete manufacture.js successfully", null);
        } catch (Exception e) {
            String message = "Item is referenced by other tables or not found";
            return errorResponse(message);
        }
    }

}
