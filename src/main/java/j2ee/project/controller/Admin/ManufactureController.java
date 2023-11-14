package j2ee.project.controller.Admin;

import j2ee.project.controller.Controller;
import j2ee.project.models.Manufacture;
import j2ee.project.service.ManufactureService;
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
@RequestMapping("/api/admin/manufacture")
@CrossOrigin(origins = "http://127.0.0.1:5500")
public class ManufactureController extends Controller {
    private static final String IMAGE_FOLDER = "manufacture";
    @Autowired
    private ManufactureService manufactureService;

    @GetMapping("")
    @ResponseBody
    public ResponseEntity<String> getAllManufacture(@RequestParam(defaultValue = "1") int page,
                                                    @RequestParam(defaultValue = "10") int size,
                                                    @RequestParam(defaultValue = "id") String sortBy,
                                                    @RequestParam(defaultValue = "ASC") String sortType,
                                                    @RequestParam(defaultValue = "") String name
                                                    ) {
        try {
            List<Manufacture> listManufacture = manufactureService.getAllManufacture(page, size, sortBy, sortType,name);
            //foreach to set image path
            //meta data
            long totalItems = manufactureService.count(name);
            Map<String, Object> metaData = buildPage(totalItems, page, size);
            return this.successResponse("Get all manufacture.js successfully", listManufacture, metaData);
        } catch (Exception e) {
            return errorResponse(e.getMessage());
        }
    }

    // READ - Get a Manufactory by ID
    @GetMapping("/{id}")
    public ResponseEntity<String> getManufactureById(@PathVariable int id) {
        Optional<Manufacture> manufactory = manufactureService.getManufactureById(id);
        if (manufactory.isPresent()) {
            return successResponse("Get manufacture.js by id successfully", manufactory.get());
        } else {
            return errorResponse("Manufacture not found");
        }

    }

    @PostMapping("")
    public ResponseEntity<String> addManufacture(@ModelAttribute Manufacture manufacture,
                                                 @RequestParam(value = "image", required = false) MultipartFile imageFile) {
        try {
            if (imageFile != null && !imageFile.isEmpty()) {
                // Save the image file and get the file path
                String imagePath = this.manufactureService.buildImagePath(imageFile, IMAGE_FOLDER);
                // Set the image path in the Manufacture object
                manufacture.setImagePath(imagePath);
            }
            Manufacture newManufacture = manufactureService.addManufactory(manufacture);
            return successResponse("Add manufacture.js successfully", newManufacture);
        } catch (Exception e) {
            return errorResponse(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateManufacture(@PathVariable int id,
                                                    @Valid @ModelAttribute Manufacture updatedManufacture,
                                                    @RequestParam(value = "image", required = false) MultipartFile imageFile,
                                                    BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder("Validation errors: ");
            bindingResult.getAllErrors().forEach(error -> {
                errorMessage.append(error.getDefaultMessage()).append("; ");
            });
            return errorResponse(errorMessage.toString());
        }

        try {
            if (imageFile != null && !imageFile.isEmpty()) {
                String imagePath = this.manufactureService.buildImagePath(imageFile, IMAGE_FOLDER);
                updatedManufacture.setImagePath(imagePath);
            }
            Manufacture updated = manufactureService.updateManufacture(id, updatedManufacture);
            return successResponse("Update manufacture.js successfully", updated);
        } catch (Exception e) {
            return errorResponse(e.getMessage());
        }
    }


    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteManufactory(@PathVariable int id) {
        manufactureService.deleteManufacture(id);
        return successResponse("Delete manufacture.js successfully", null);
    }


}
