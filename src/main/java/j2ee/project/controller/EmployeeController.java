package j2ee.project.controller;

import ch.qos.logback.core.model.Model;
import com.fasterxml.jackson.databind.ObjectMapper;
import j2ee.project.models.Employee;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@org.springframework.stereotype.Controller
@RequestMapping("/employees")
public class EmployeeController extends Controller{
    @GetMapping("/form")
    public String showForm(Employee employeeDTO) {
        return "user/employee-form.html"; // Tạo một file HTML tên là "employee-form.html" để hiển thị form nhập liệu
    }
    @PostMapping("/create")
    @ResponseBody
    public Employee createEmployee(@RequestBody String employeeDTOJson) {
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
            employee.setBirthday(employeeDTO.getBirthday());
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

            // Đối tượng Employee đã được tạo và sẽ được trả về dưới dạng JSON
            return employee;
        } else {
            // Xử lý lỗi nếu không thể phân tích chuỗi JSON thành đối tượng EmployeeDTO
            return null;
        }
    }
}
