package j2ee.project.controller.Admin;

import j2ee.project.controller.Controller;
import j2ee.project.models.Manufacture;
import j2ee.project.service.ManufactureService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/admin/manufacture")
@CrossOrigin(origins = "http://127.0.0.1:5500")
public class ManufactureController extends Controller {
    @Autowired
    private ManufactureService manufactureService;

    @GetMapping("")
    @ResponseBody
    public ResponseEntity<String> getAllManufacture(@RequestParam(defaultValue = "1") int page,
                                                    @RequestParam(defaultValue = "10") int size,
                                                    @RequestParam(defaultValue = "id") String sortBy,
                                                    @RequestParam(defaultValue = "ASC") String sortType
    ) {
        try {
            List<Manufacture> listManufacture = manufactureService.getAllManufacture(page, size, sortBy, sortType);
            //meta data
            long totalItems = manufactureService.countAllManufacture();
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
    public ResponseEntity<String> addManufacture(@RequestBody Manufacture manufacture) {
        try {
            Manufacture newManufacture = manufactureService.addManufactory(manufacture);
            return successResponse("Add manufacture.js successfully", newManufacture);
        } catch (Exception e) {
            return errorResponse(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateManufacture(@PathVariable int id, @Valid @RequestBody Manufacture updatedManufacture, BindingResult bindingResult) {
        // @Valid @RequestBody Manufacture updatedManufacture
        if (bindingResult.hasErrors()) {
            // Handle validation errors here
            StringBuilder errorMessage = new StringBuilder("Validation errors: ");
            bindingResult.getAllErrors().forEach(error -> {
                errorMessage.append(error.getDefaultMessage()).append("; ");
            });
            return errorResponse(errorMessage.toString());
        }

        try {
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
