package j2ee.project.controller.User;

import j2ee.project.controller.Controller;
import j2ee.project.models.Product;
import j2ee.project.models.Stock;
import j2ee.project.repository.WareHouseRepository;
import j2ee.project.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/warehouse/")
public class WareHouseController extends Controller {
    @Autowired
    private WareHouseRepository wareHouseRepository;
    @Autowired
    private ProductService productService;

    @PostMapping("/update")
    @ResponseBody
    public ResponseEntity<String> updateProductInWareHouse(@RequestBody Stock wareHouse) {
        try {
            Product product = productService.getProductById(wareHouse.getProduct().getId()).get();
            if (product == null) {
                return errorResponse("Product not found.");
            }

            wareHouse.setProduct(product);
            Optional<Stock> existingWareHouse = wareHouseRepository.findByProduct(wareHouse.getProduct());
            System.out.println(existingWareHouse);

            if (existingWareHouse.isPresent()) {
                Stock storedWareHouse = existingWareHouse.get();
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
    public ResponseEntity<String> addProductToWareHouse(@RequestBody Stock wareHouse) {
        try {
            Optional<Product> productOptional = productService.getProductById(wareHouse.getProduct().getId());

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
