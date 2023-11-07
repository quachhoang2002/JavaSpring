package j2ee.project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class ClientController {

    @GetMapping("/")
    public String home(){
        return "user/index.html";
    }

    @GetMapping("/login")
    public String login(){
        return "user/login.html";
    }

    @GetMapping("/register")
    public String register(){
        return "user/register.html";
    }

    @GetMapping("/viewCart")
    public String cart(){
        return "user/cart.html";
    }
    @GetMapping("/user")
    public String user(){
        return "user/user.html";
    }
    @GetMapping("/editInfo")
    public String editInfo(){
        return "user/editInfo.html";
    }
    @GetMapping("/getOrders")
    public String getOrders(){
        return "user/orders.html";
    }
    @GetMapping("/getItem/{productId}")
    public String getItem(@PathVariable("productId") Integer productId, Model model) {
        model.addAttribute("productId", productId);
        return "user/item";
    }

    @GetMapping("/getOrderDetail/{productId}")
    public String getOrderDetail(@PathVariable("productId") Integer orderId) {
        return "user/orderDetail";
    }
}
