package j2ee.project.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/caltamgiac/")
public class TamgiacController {
    @GetMapping("/tamgiac")
    public String tamGiacForm() {
        return "user.tamgiac.html";
    }
    @PostMapping("/tinh")
    @ResponseBody
    public String tinh(
            @RequestParam("a") double a,
            @RequestParam("b") double b,
            @RequestParam("c") double c,
            @RequestParam("action") String action) {

        if ("dien-tich".equals(action)) {
            if( (a + b > c) && (a + c > b) && (b + c > a) ) {
                // Tính diện tích tam giác
                double p = (a + b + c) / 2; // Chu vi tam giác
                double area = Math.sqrt(p * (p - a) * (p - b) * (p - c)); // Diện tích tam giác
                return "Diện tích: " + area;
            }else {
                return "Chiều dài các cạnh không hợp lệ";
            }

        } else if ("chu-vi".equals(action)) {
            if( (a + b > c) && (a + c > b) && (b + c > a) ) {
                double perimeter = a + b + c; // Chu vi tam giác
                return "Chu vi: " + perimeter;
            }else {
                return "Chiều dài các cạnh không hợp lệ";
            }


        } else {
            return "Hành động không hợp lệ";
        }
    }
}
