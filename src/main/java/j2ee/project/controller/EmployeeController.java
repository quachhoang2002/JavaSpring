package j2ee.project.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import j2ee.project.models.Employee;
import org.springframework.beans.BeanUtils;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class EmployeeController {
    @GetMapping("/form-employ")
    public String showForm() {
        return "user/employee-form.html"; }

    @PostMapping("/create")
    @ResponseBody
    public Employee createEmployee(@RequestPart("data") String employeeDTOJson, @RequestPart("imageFile") MultipartFile imageFile) {
        // Sử dụng ObjectMapper để phân tích chuỗi JSON thành một đối tượng EmployeeDTO
        ObjectMapper objectMapper = new ObjectMapper();
        Employee employeeDTO = null;
        try {
            employeeDTO = objectMapper.readValue(employeeDTOJson, Employee.class);
        } catch (IOException e) {
            e.printStackTrace();
            // Xử lý lỗi nếu có lỗi khi phân tích chuỗi JSON
        }

        if (employeeDTO != null) {
            // Tạo một đối tượng Employee
            Employee employee = new Employee();

            // Sao chép các thuộc tính cơ bản
            employee.setFullname(employeeDTO.getFullname());
            Date birthday = employeeDTO.getBirthday();
            employee.setBirthday(birthday);
            employee.setGender(employeeDTO.isGender());
            employee.setCountry(employeeDTO.getCountry());
            employee.setMarried(employeeDTO.isMarried());
            employee.setNotes(employeeDTO.getNotes());

            // Xử lý mảng "hobbies" từ chuỗi JSON
            if (employeeDTO.getHobbies() != null) {
                // Chuyển đổi chuỗi hobbies từ định dạng JSON sang mảng chuỗi
                String[] hobbiesArray = objectMapper.convertValue(employeeDTO.getHobbies(), String[].class);

                // Thiết lập mảng chuỗi vào đối tượng Employee
                employee.setHobbies(hobbiesArray);
            } else {
                // Nếu mảng "hobbies" trong JSON là null, thiết lập mảng rỗng
                employee.setHobbies(new String[0]);
            }

            if (!imageFile.isEmpty()) {
                try {
                    // Save the image to a specific directory or database
                    // You can use a service or repository for this purpose
                    byte[] imageBytes = imageFile.getBytes();
                    // Save the image data as needed

                    // Set the image URL or identifier in your employee object
                    // For example, you can store the image URL
                    String imageUrl = "/images/" + imageFile.getOriginalFilename(); // This is just an example URL
                    employee.setImageUrl(imageUrl);
                } catch (IOException e) {
                    e.printStackTrace();
                    // Handle the exception if there's an issue with image file processing
                }
            }

            // Đối tượng Employee đã được tạo và sẽ được trả về dưới dạng JSON
            return employee;
        } else {
            // Xử lý lỗi nếu không thể phân tích chuỗi JSON thành đối tượng EmployeeDTO
            return null;
        }
    }


}
