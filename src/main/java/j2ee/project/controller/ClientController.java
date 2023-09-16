package j2ee.project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class ClientController {


    @GetMapping("/login")
    public String home(){
        return "user/home.html";
    }

    @GetMapping("/client")
    public String index(){
        return "user/client.html";
    }

}
