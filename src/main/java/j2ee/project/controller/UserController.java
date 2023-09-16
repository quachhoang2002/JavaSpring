package j2ee.project.controller;

import j2ee.project.models.User;
import j2ee.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user/")
public class UserController {
    @Autowired
    private UserService userService;


    @PostMapping("/register")
    public ResponseEntity<String>register(@RequestBody User user){
        userService.register(user);
        return ResponseEntity.ok("User registered successfully");
    }
}
