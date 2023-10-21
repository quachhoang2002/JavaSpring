package j2ee.project.controller.User;

import j2ee.project.models.Product;
import j2ee.project.models.WareHouse;
import j2ee.project.repository.WareHouseRepository;
import j2ee.project.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/warehouse/")
public class WareHouseController extends Controller{
    @Autowired
    private WareHouseRepository wareHouseRepository;
    @Autowired
    private ProductService productService;
    @PostMapping("/update")
    @ResponseBody
    public ResponseEntity<String> updateProductInWareHouse(@RequestBody WareHouse wareHouse) {
        try {
            Optional<WareHouse> existingWareHouse = wareHouseRepository.findByProductId(wareHouse.getProductId());

            if (existingWareHouse.isPresent()) {
                WareHouse storedWareHouse = existingWareHouse.get();
                int newQuantity = storedWareHouse.getQuantity() + wareHouse.getQuantity(); // Tính toán quantity mới
                storedWareHouse.setQuantity(newQuantity); // Cập nhật quantity theo giá trị mới
                wareHouseRepository.save(storedWareHouse);
                return successResponse("Updated successfully in the warehouse.", null);
            } else {
                return errorResponse("Product not found in the warehouse.");
            }
        } catch (Exception e) {
            return errorResponse(e.getMessage());
        }
    }

    @PostMapping("/add")
    @ResponseBody
    public ResponseEntity<String> addProductToWareHouse(@RequestBody WareHouse wareHouse) {
        try {
            System.out.println(wareHouse.getProductId());
            Optional<Product> productOptional = productService.getProductById(wareHouse.getProductId());

            if (productOptional.isPresent()) {
                // Sản phẩm tồn tại, thêm vào kho
                wareHouseRepository.save(wareHouse);
                return successResponse("Added successfully to the warehouse.", null);
            } else {
                return errorResponse("Product not found.");
            }
        } catch (Exception e) {
            return errorResponse(e.getMessage());
        }
    }

}
