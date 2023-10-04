package j2ee.project.controller;

import j2ee.project.models.User;
import j2ee.project.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/user/")
public class UserController extends Controller {
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
//    @GetMapping("/login")
//    public String showLoginForm(HttpServletRequest request, Model model) {
//        Cookie[] cookies = request.getCookies();
//        if (cookies != null) {
//            for (Cookie cookie : cookies) {
//                if ("username".equals(cookie.getName())) {
//                    model.addAttribute("username", cookie.getValue());
//                    break;
//                }
//            }
//        }
//        return "login";
//    }

    @PostMapping("/login")
    @ResponseBody
    public ResponseEntity<String> login(@RequestBody Map<String, String> body, HttpSession session) {
        try {
            String email = body.get("email");
            String password = body.get("password");
            boolean rememberMe = Boolean.parseBoolean(body.get("rememberMe"));

            User user = userService.login(email, password);
            if (user != null) {
                session.setAttribute("loggedIn", true);
                session.setAttribute("userData", user);
                Map<String, Object> responseData = new HashMap<>();
                HttpHeaders headers = null;
                if (rememberMe) {
                    // Tạo và đặt cookie nếu cần
                    Cookie cookie = new Cookie("rememberMe", "true");
                    cookie.setMaxAge(30 * 24 * 60 * 60);
                    headers = new HttpHeaders();
                    headers.add(HttpHeaders.SET_COOKIE, cookie.toString());
                    responseData.put("rememberMe", headers);

                }
                responseData.put("email", email);
                responseData.put("password", password);
                responseData.put("loggedIn", true);
                responseData.put("message", "Login successfully");
                responseData.put("userData", user);

                return successResponse("me may", user);

            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid credentials");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Server error");
        }
    }


    @GetMapping("/check-login")
    public ResponseEntity<?> checkLoginStatus(HttpSession session) {
        // Kiểm tra trạng thái đăng nhập dựa trên session
        Boolean loggedIn = (Boolean) session.getAttribute("loggedIn");

        if (loggedIn != null && loggedIn) {
            // Đã đăng nhập, trả về thông báo hoặc dữ liệu về người dùng
            Map<String, Object> responseData = new HashMap<>();
            responseData.put("loggedIn", true);
            responseData.put("message", "Đã đăng nhập");
            return ResponseEntity.ok(responseData);
        }
        return null;
    }
    @GetMapping("/logout")
    public ResponseEntity<String> logout(HttpSession session) {
        try {
            session.removeAttribute("loggedIn");
            session.removeAttribute("userData");
            return successResponse("Logout successfully", null);
        } catch (Exception e) {
            return errorResponse(e.getMessage());
        }
    }

}
