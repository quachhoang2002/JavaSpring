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
            String token = body.get("token");
            if (token != null){
                User user = userService.findByToken(token);
                if (user != null){
                    return successResponse("Login successfully", user);
                }
            }

            String email = body.get("email");
            String password = body.get("password");
            //if have remember me;
            boolean remember = Boolean.parseBoolean(body.get("remember"));
            System.out.println("email: " + email + " password: " + password);
            User user = userService.login(email, password);
            if (remember){
                //random token
                String newToken = java.util.UUID.randomUUID().toString();
                user.setToken(newToken);
                userService.remember(user);
            }
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

}
