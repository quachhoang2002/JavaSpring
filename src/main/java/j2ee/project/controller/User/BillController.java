package j2ee.project.controller.User;

import j2ee.project.models.*;
import j2ee.project.repository.BillDetailsRepository;
import j2ee.project.repository.BillRepository;
import j2ee.project.repository.CartRepository;
import j2ee.project.repository.WareHouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/bill/")
public class BillController extends Controller{
    @Autowired
    private BillRepository billRepository;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private BillDetailsRepository billDetailsRepository;
    @Autowired
    private WareHouseRepository wareHouseRepository;

    @PostMapping("/add")
    @ResponseBody
    public ResponseEntity<String> addNewBill(@RequestBody Bill bill) {
        try {
            Bill newBill = billRepository.save(bill);

            List<Cart> cartItems = cartRepository.findAll();

            for (Cart cartItem : cartItems) {
                BillDetails billDetails = new BillDetails();
                billDetails.setBillId(newBill.getId());
                billDetails.setProductId(cartItem.getProductId());
                billDetails.setQuantity(cartItem.getQuantity());
                billDetails.setPrice(cartItem.getPrice());
                billDetailsRepository.save(billDetails);

                Optional<WareHouse> wareHouseOptional = wareHouseRepository.findByProductId(cartItem.getProductId());

                if (wareHouseOptional.isPresent()) {
                    WareHouse wareHouse = wareHouseOptional.get(); // Lấy đối tượng WareHouse từ Optional
                    int newQuantity = wareHouse.getQuantity() - cartItem.getQuantity();
                    wareHouse.setQuantity(newQuantity);
                    wareHouseRepository.save(wareHouse);
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
