package j2ee.project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
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


}
