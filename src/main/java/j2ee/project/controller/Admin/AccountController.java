package j2ee.project.controller.Admin;

import j2ee.project.controller.Controller;
import j2ee.project.models.Admin;
import j2ee.project.models.Manufacture;
import j2ee.project.repository.AdminRepository;
import j2ee.project.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/account")
public class AccountController extends Controller {
    // get list
    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private AdminService adminService;


    //get list account
    @GetMapping("")
    @ResponseBody
    public ResponseEntity<String> getAllAccount(@RequestParam(defaultValue = "1") int page,
                                                    @RequestParam(defaultValue = "10") int size,
                                                    @RequestParam(defaultValue = "") String name
    ) {
        try {
            List<Admin> listManufacture = adminService.getAll(page, size,name);
            //foreach to set image path
            //meta data
            long totalItems = adminService.count(name);
            Map<String, Object> metaData = buildPage(totalItems, page, size);
            return this.successResponse("Get all admin.js successfully", listManufacture, metaData);
        } catch (Exception e) {
            return errorResponse(e.getMessage());
        }
    }

    //get detail
    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<String> getAccount(@PathVariable("id") int id) {
        try {
            Admin admin = adminRepository.findById(id).get();
            return this.successResponse("Get admin.js successfully", admin);
        } catch (Exception e) {
            return errorResponse(e.getMessage());
        }
    }

    //create account
    @PostMapping("")
    @ResponseBody
    public ResponseEntity<String> createAccount(@ModelAttribute Admin admin) {
        try {
            Admin newManufacture = adminRepository.save(admin);
            return this.successResponse("Create admin.js successfully", newManufacture);
        } catch (Exception e) {
            return errorResponse(e.getMessage());
        }
    }

    //update account
    @PutMapping("/{id}")
    @ResponseBody
    public ResponseEntity<String>UpdateAccount(
            @PathVariable("id") int id,
            @ModelAttribute Admin admin
    ) {
        try {
            Admin oldaAdmin = adminRepository.findById(id).get();
            oldaAdmin.setName(admin.getName());
            oldaAdmin.setEmail(admin.getEmail());
            oldaAdmin.setPassword(admin.getPassword());
            oldaAdmin.setRole(admin.getRole());
            Admin newManufacture = adminRepository.save(oldaAdmin);
            return this.successResponse("Update admin.js successfully", newManufacture);
        } catch (Exception e) {
            return errorResponse(e.getMessage());
        }
    }


}
