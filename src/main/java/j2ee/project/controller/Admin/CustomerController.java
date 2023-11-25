package j2ee.project.controller.Admin;

import j2ee.project.controller.Controller;
import j2ee.project.models.User;
import j2ee.project.repository.UserRepository;
import j2ee.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/customer")
public class CustomerController extends Controller {
    // get list
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    //get list account
    @GetMapping("")
    @ResponseBody
    public ResponseEntity<String> getAllAccount(@RequestParam(defaultValue = "1") int page,
                                                @RequestParam(defaultValue = "10") int size,
                                                @RequestParam(defaultValue = "") String name,
                                                @RequestParam(defaultValue = "") Integer CustomerStatus
    ) {
        try {
            List<User> listManufacture = userService.getAll(page, size, name, CustomerStatus);
            //foreach to set image path
            //meta data
            long totalItems = userService.count(name, CustomerStatus);
            Map<String, Object> metaData = buildPage(totalItems, page, size);
            return successResponse("Get all admin.js successfully", listManufacture, metaData);
        } catch (Exception e) {
            return errorResponse(e.getMessage());
        }
    }

    //get detail
    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<String> getAccount(@PathVariable("id") int id) {
        try {
            User admin = userService.findById(id);
            return this.successResponse("Get admin.js successfully", admin);
        } catch (Exception e) {
            return errorResponse(e.getMessage());
        }
    }

    //create account
    @PostMapping("")
    @ResponseBody
    public ResponseEntity<String> create(@RequestBody User user) {
        try {
            user.setStatus(1);
            userService.register(user);
            return successResponse("Register successfully", null);
        } catch (Exception e) {
            return errorResponse(e.getMessage());
        }
    }

    //update account
    @PutMapping("/{id}")
    @ResponseBody
    public ResponseEntity<?> editUser(@RequestBody User user, @PathVariable("id") int id) {
        User existedUser = userService.findById(id);
        if (existedUser == null) {
            return errorResponse("User not found");
        }
        try{
            userService.updateUserByEmail(id, user);
            return successResponse("Update user successfully", null);
        }catch (Exception e){
            return errorResponse(e.getMessage());
        }
    }


    //delete account
    @DeleteMapping("/{id}")
    @ResponseBody
    public ResponseEntity<String> deleteAccount(@PathVariable("id") int id) {
        try {
            userRepository.deleteById(id);
            return this.successResponse("Delete admin.js successfully", null);
        } catch (Exception e) {
            return errorResponse("Delete user failed");
        }
    }

    //block
    @PutMapping("{id}/block")
    @ResponseBody
    public ResponseEntity<String> blockAccount(@PathVariable("id") int id) {
        try {
            User user = userRepository.findById(id);
            if (user == null) {
                return errorResponse("User not found");
            }

            if (user.getStatus() == 0) {
                user.setStatus(1);
            }else {
                user.setStatus(0);
            }
            userRepository.save(user);
            return this.successResponse("Block user successfully", null);
        } catch (Exception e) {
            return errorResponse("Block user failed");
        }
    }


}
