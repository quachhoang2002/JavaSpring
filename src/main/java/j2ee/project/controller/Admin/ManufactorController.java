package j2ee.project.controller.Admin;

import j2ee.project.controller.Controller;
import j2ee.project.models.Manufactory;
import j2ee.project.service.ManufactoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/manufactor")
public class ManufactorController extends Controller {


    @Autowired
    private ManufactoryService manufactoryService;

    @GetMapping("/")
    @ResponseBody
    public ResponseEntity<String> getAllManufactor() {
        try{
            List<Manufactory> ListManufactory = manufactoryService.getAllManufactories();
            return successResponse("List all manufactor", ListManufactory);
        }catch (Exception e){
            return errorResponse(e.getMessage());
        }
    }

    @PostMapping("/")
    public ResponseEntity<String> addManufactory(@RequestBody Manufactory manufactory) {
        try{
            Manufactory newManufactory = manufactoryService.addManufactory(manufactory);
            return successResponse("Add manufactor successfully", newManufactory);
        }
        catch (Exception e){
            return errorResponse(e.getMessage());
        }
    }


}
