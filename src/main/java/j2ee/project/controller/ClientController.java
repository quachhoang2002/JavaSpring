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
        return "user/home.html";
    }

    @GetMapping("/login")
    public String login(){
        return "user/login.html";
    }

    @GetMapping("/register")
    public String register(){
        return "user/register.html";
    }

    @GetMapping("/contact")
    public String contact(){
        return "user/contact.html";
    }
    @GetMapping("/cart")
    public String cart(){
        return "user/cart.html";
    }
    @GetMapping("/viewcart")
    public String viewcart(){
        return "user/viewcart.html";
    }
    @GetMapping("/getItem/{productId}")
    public String getItem(@PathVariable("productId") Integer productId, Model model) {
        model.addAttribute("productId", productId);
        return "user/item";
    }


}
