package j2ee.project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;

@Controller
@RequestMapping("/info/")
public class InfoController {
    @PostMapping("/dang-ky")
    @ResponseBody
    public String dangKy(
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            @RequestParam("gender") String gender,
            @RequestParam(name = "married", defaultValue = "false") boolean married,
            @RequestParam("country") String country,
            @RequestParam("notes") String notes,
            @RequestParam("hobbies") String[] hobbies
           ) {
        String g = "";
        if(gender.equals("true")){
            g = "Nam";
        }else {
            g = "Nữ";
        }
        return "<h1>Kết quả Đăng ký</h1>" +
                "<p>Tên đăng nhập: " + username + "</p>" +
                " <p>Mật khẩu: " + password + "</p>" +
                " <p>Giới tính: " + g + "</p>" +
                " <p>Đã kết hôn: " + married + "</p>" +
                " <p>Quốc tịch: " + country + "</p>" +
                " <p>Sở thích: " + Arrays.toString(hobbies) + "</p>" +
                " <p>Ghi chú: " + notes + "</p>"; // Trả về tên trang HTML để hiển thị dữ liệu
    }
}
