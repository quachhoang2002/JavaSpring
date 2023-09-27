package j2ee.project.controller.User;

import j2ee.project.controller.Controller;
import j2ee.project.models.User;
import j2ee.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class LoginController extends Controller {
    @Autowired
    private UserService userService;


    @PostMapping("/register")
    @ResponseBody
    public ResponseEntity<String> register(@RequestBody User user) {
        try {
            userService.register(user);
            return successResponse("Register successfully", null);
        } catch (Exception e) {
            return errorResponse(e.getMessage());
        }
    }

    @PostMapping("/login")
    @ResponseBody
    public ResponseEntity<String> login(@RequestBody Map<String, String> body) {
        try {
            String email = body.get("email");
            String password = body.get("password");
            System.out.println("email: " + email + " password: " + password);
            userService.login(email, password);
            return successResponse("Login successfully", null);
        } catch (Exception e) {
            return errorResponse(e.getMessage());
        }
    }
}
