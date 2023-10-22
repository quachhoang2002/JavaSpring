package j2ee.project.controller.User;

import j2ee.project.controller.Controller;
import j2ee.project.models.*;
import j2ee.project.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/bill/")
public class BillController extends Controller {
    @Autowired
    private BillRepository billRepository;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private BillDetailsRepository billDetailsRepository;
    @Autowired
    private StockRepository stockRepository;

    private ProductRepository productRepository;

    @PostMapping("/add")
    @ResponseBody
    public ResponseEntity<String> addNewBill(@RequestBody Order bill) {
        try {
            Order newBill = billRepository.save(bill);

            List<Cart> cartItems = cartRepository.findAll();

            for (Cart cartItem : cartItems) {
                OrderDetails billDetails = new OrderDetails();
                billDetails.setQuantity(cartItem.getQuantity());
                billDetails.setPrice(cartItem.getPrice());
                billDetailsRepository.save(billDetails);

                Product product = productRepository.findById(cartItem.getProductId()).get();
                billDetails.setProduct(product);

                Optional<Stock> wareHouseOptional = stockRepository.findByProduct(billDetails.getProduct());

                if (wareHouseOptional.isPresent()) {
                    Stock wareHouse = wareHouseOptional.get(); // Lấy đối tượng WareHouse từ Optional
                    int newQuantity = wareHouse.getQuantity() - cartItem.getQuantity();
                    wareHouse.setQuantity(newQuantity);
                    stockRepository.save(wareHouse);
                } else {
                    // Xử lý trường hợp không tìm thấy WareHouse
                }

            }

            cartRepository.deleteAll();

            return successResponse("Added new bill and details successfully.", null);
        } catch (Exception e) {
            return errorResponse(e.getMessage());
        }
    }

}
