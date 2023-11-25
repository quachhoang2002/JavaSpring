package j2ee.project.controller.User;

import j2ee.project.controller.Controller;
import j2ee.project.models.Admin;
import j2ee.project.models.User;
import j2ee.project.service.UserService;
import j2ee.project.ultils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class LoginController extends Controller {
    @Autowired
    private UserService userService;


    private String createToken() {
        //random 10 character + time is 1 day
        String token = java.util.UUID.randomUUID().toString();
        token = token.substring(0, 10) + System.currentTimeMillis();
        return token;
    }

    @GetMapping("/check-token")
    @ResponseBody
    public ResponseEntity<String> checkToken(@RequestParam String token) {
        try {
            if (ultils.isValidToken(token)) {
                return this.successResponse("Token is valid", null);
            }
            //find by token
            User user = userService.findByToken(token);
            if (user != null) {
                return this.successResponse("Token is valid", null);
            }
            return this.errorResponse("Token is invalid");
        } catch (Exception e) {
            return errorResponse(e.getMessage());
        }
    }

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
            String token = body.get("token");
            if (token != null) {
                User user = userService.findByToken(token);
                if (user != null) {
                    return successResponse("Login successfully", user);
                }
            }

            String email = body.get("email");
            String password = body.get("password");
            //if have remember me;

            User user = userService.login(email, password);
            String newToken = createToken();
            user.setToken(newToken);
            userService.remember(user);
            return successResponse("Login successfully", user);
        } catch (Exception e) {
            return errorResponse(e.getMessage());
        }
    }

    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public ResponseEntity<String> logout(@RequestBody Map<String, String> body) {
        try {
            String token = body.get("token");
            if (token != null) {
                User user = userService.findByToken(token);
                System.out.println(user);

                if (user != null) {
                    userService.update(user);
                    return successResponse("Logged out successfully", null);
                }
            }

            return errorResponse("Invalid token"); // Token không hợp lệ
        } catch (Exception e) {
            return errorResponse(e.getMessage());
        }
    }

    @PutMapping("/editUser")
    @ResponseBody
    public ResponseEntity<?> editUser(@RequestBody User user) {
        User existedUser = userService.findById(user.getId());
        userService.updateUserByEmail(user.getId(), user);
        return successResponse("success", userService.updateUserByEmail(user.getId(), user));
    }

}
