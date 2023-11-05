package j2ee.project.controller.User;

import j2ee.project.controller.Controller;
import j2ee.project.models.Cart;
import j2ee.project.models.Product;
import j2ee.project.models.User;
import j2ee.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping("/api/user/")
public class UserController extends Controller {
    @Autowired
    private UserService userService;
    @GetMapping("/getInfo/{userId}")
    public ResponseEntity<?> getByUserId(@PathVariable int userId) {
        User user = userService.findById(userId);
        if(user != null){
            return successResponse("Success", user);
        }else {
            return errorResponse("fail");
        }
    }

}
