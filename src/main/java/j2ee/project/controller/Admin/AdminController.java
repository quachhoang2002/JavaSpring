package j2ee.project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AdminController {
    //admin
    @GetMapping("/admin")
    public String admin(){
        return "admin/index.html";
    }

    @GetMapping("/admin/login")
    public String login(){
        return "admin/login.html";
    }

    @GetMapping("/admin/manufacture")
    public String manufacture(){
        return "admin/manufacture.html";
    }

    @GetMapping("/admin/category")
    public String category(){
        return "admin/category.html";
    }
}
