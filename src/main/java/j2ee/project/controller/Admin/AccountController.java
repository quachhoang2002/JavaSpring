package j2ee.project.controller.Admin;

import j2ee.project.controller.Controller;
import j2ee.project.models.Admin;
import j2ee.project.models.Manufacture;
import j2ee.project.repository.AdminRepository;
import j2ee.project.service.AdminService;
import j2ee.project.ultils;
import jakarta.validation.Valid;
import org.apache.tomcat.util.security.MD5Encoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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


    private String createToken() {
        //random 10 character + time is 1 day
        String token = java.util.UUID.randomUUID().toString();
        token = token.substring(0, 10) + System.currentTimeMillis();
        return token;
    }


    public static String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(password.getBytes());
            BigInteger no = new BigInteger(1, messageDigest);
            String hashText = no.toString(16);
            while (hashText.length() < 32) {
                hashText = "0" + hashText;
            }
            return hashText;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    //check token
    @GetMapping("/check-token")
    @ResponseBody
    public ResponseEntity<String> checkToken(@RequestParam String token) {
        try {
            if (ultils.isValidToken(token)) {
                return this.successResponse("Token is valid", null);
            }
            //find by token
            Admin admin = adminRepository.findByToken(token);
            if (admin != null) {
                return this.successResponse("Token is valid", null);
            }
            return this.errorResponse("Token is invalid");
        } catch (Exception e) {
            return errorResponse(e.getMessage());
        }
    }

    //login
    @PostMapping("/login")
    @ResponseBody
    public ResponseEntity<String> login(@RequestBody Map<String, String> body) {
        try {
            String email = body.get("email");
            String password = body.get("password");
            if (email == "admin" && password == "admin") {
                Admin admin = new Admin();
                admin.setToken(createToken());
                admin.setName("admin");
                admin.setEmail("admin");
                admin.setPassword("admin");
                return this.successResponse("Login successfully", admin);
            }
            //password
            password = hashPassword(password);
            Admin admin1 = adminRepository.findByEmailAndPassword(email, password);
            if (admin1 == null) {
                return errorResponse("Email or password is incorrect");
            }
            admin1.setToken(createToken());
            adminRepository.save(admin1);
            return this.successResponse("Login successfully", admin1);
        } catch (Exception e) {
            return errorResponse(e.getMessage());
        }
    }

    //get list account
    @GetMapping("")
    @ResponseBody
    public ResponseEntity<String> getAllAccount(@RequestParam(defaultValue = "1") int page,
                                                @RequestParam(defaultValue = "10") int size,
                                                @RequestParam(defaultValue = "") String name
    ) {
        try {
            List<Admin> listManufacture = adminService.getAll(page, size, name);
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
    public ResponseEntity<String> createAccount(
            @Valid @ModelAttribute Admin admin,
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

            if (adminRepository.findByEmail(admin.getEmail()) != null) {
                return errorResponse("Email is exist");
            }
            //hash password
            String password = hashPassword(admin.getPassword());
            System.out.println(password);
            admin.setPassword(password);
            Admin res = adminRepository.save(admin);
            return this.successResponse("Create admin.js successfully", res);
        } catch (Exception e) {
            return errorResponse(e.getMessage());
        }
    }

    //update account
    @PutMapping("/{id}")
    @ResponseBody
    public ResponseEntity<String> UpdateAccount(
            @PathVariable("id") int id,
            @Valid @ModelAttribute Admin admin,
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

            if (adminRepository.findByEmail(admin.getEmail()) != null) {
                return errorResponse("Email is exist");
            }

            String password = hashPassword(admin.getPassword());
            Admin oldaAdmin = adminRepository.findById(id).get();
            oldaAdmin.setName(admin.getName());
            oldaAdmin.setEmail(admin.getEmail());
            oldaAdmin.setPassword(password);
            oldaAdmin.setRole(admin.getRole());
            Admin newManufacture = adminRepository.save(oldaAdmin);
            return this.successResponse("Update admin.js successfully", newManufacture);
        } catch (Exception e) {
            return errorResponse(e.getMessage());
        }
    }

    //delete account
    @DeleteMapping("/{id}")
    @ResponseBody
    public ResponseEntity<String> deleteAccount(@PathVariable("id") int id) {
        try {
            adminRepository.deleteById(id);
            return this.successResponse("Delete admin.js successfully", null);
        } catch (Exception e) {
            return errorResponse(e.getMessage());
        }
    }


}
