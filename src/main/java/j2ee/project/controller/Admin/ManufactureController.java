package j2ee.project.controller.Admin;

import j2ee.project.controller.Controller;
import j2ee.project.models.Manufacture;
import j2ee.project.service.ManufactureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/admin/manufacture")
@CrossOrigin(origins = "http://127.0.0.1:5500")
public class ManufactureController extends Controller {
    @Autowired
    private ManufactureService manufactureService;

    @GetMapping("")
    @ResponseBody
    public ResponseEntity<String> getAllManufacture() {
        try{
            List<Manufacture> listManufacture = manufactureService.getAllManufacture();
            return successResponse("List all manufacture.js", listManufacture);
        }catch (Exception e){
            return errorResponse(e.getMessage());
        }
    }

    // READ - Get a Manufactory by ID
    @GetMapping("/{id}")
    public ResponseEntity<Manufacture> getManufactureById(@PathVariable int id) {
        Optional<Manufacture> manufactory = manufactureService.getManufactureById(id);
        if (manufactory.isPresent()) {
            return ResponseEntity.ok(manufactory.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("")
    public ResponseEntity<String> addManufacture(@RequestBody Manufacture manufacture) {
        try{
            Manufacture newManufacture = manufactureService.addManufactory(manufacture);
            return successResponse("Add manufacture.js successfully", newManufacture);
        }
        catch (Exception e){
            return errorResponse(e.getMessage());
        }
    }

    @PutMapping("")
    public ResponseEntity<String> updateManufacture(@PathVariable int id, Manufacture updatedManufacture){
        try{
            Manufacture updated = manufactureService.updateManufacture(id, updatedManufacture);
            return successResponse("Update manufacture.js successfully", updated);
        }catch (Exception e){
            return errorResponse(e.getMessage());

        }
    }


    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteManufactory(@PathVariable int id) {
        manufactureService.deleteManufacture(id);
        return ResponseEntity.noContent().build();
    }


}
