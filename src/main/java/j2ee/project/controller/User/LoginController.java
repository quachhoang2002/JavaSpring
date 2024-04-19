package j2ee.project.controller.User;

import j2ee.project.DTO.UpdateUserRequest;
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
            if (user.getStatus() == 0) {
                return this.errorResponse("Account is blocked");
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
            user.setStatus(1);
            user.setIsThirdParty(false);
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
            if (body.get("isThirdParty") != null) {
                User user = userService.findByEmail(body.get("email"));
                if (user == null) {
                    user = new User();
                    user.setEmail(body.get("email"));
                    user.setName(body.get("name"));
                    user.setPhone(body.get("phone"));
                    user.setProviderName(body.get("providerName"));
                    user.setIsThirdParty(true);
                    user.setPassword(java.util.UUID.randomUUID().toString().substring(0, 8));
                    user.setToken(createToken());
                    user.setStatus(1);
                    userService.register(user);
                    return successResponse("Login successfully", user);
                }

                user = userService.findByEmail(body.get("email"));
                if (user.getStatus() == 0) {
                    return errorResponse("Account is blocked");
                }
                String newToken = createToken();
                user.setToken(newToken);
                userService.remember(user);
                return successResponse("Login successfully", user);
            }

            String token = body.get("token");
            if (token != null) {
                User user = userService.findByToken(token);
                if (user != null) {
                    if (user.getStatus() == 0) {
                        return errorResponse("Account is blocked");
                    }

                    return successResponse("Login successfully", user);
                }
            }

            String email = body.get("email");
            String password = body.get("password");
            //if have remember me;

            User user = userService.login(email, password);
            System.out.println(user.getStatus());
            if (user.getStatus() == 0) {
                return errorResponse("Account is blocked");
            }

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
    public ResponseEntity<?> editUser(@RequestBody UpdateUserRequest user) {
        User existedUser = userService.findById(user.getId());
        if (existedUser == null) {
            return errorResponse("User not found");
        }

        existedUser.setName(user.getName());
        existedUser.setPhone(user.getPhone());
        existedUser.setEmail(user.getEmail());
        if (user.getPassword() != null) {
            existedUser.setPassword(user.getPassword());
        }

        User updateU = userService.updateUserByEmail(user.getId(), existedUser);
        return successResponse("success",updateU);
    }


    //random password
}
