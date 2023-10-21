package j2ee.project.controller;

import j2ee.project.models.*;
import j2ee.project.repository.WareHouseRepository;
import j2ee.project.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/cart/")
public class CartController extends Controller{
    @Autowired
    private CartService cartService;
    @Autowired
    private WareHouseRepository wareHouseRepository;
    @PostMapping("/add")
    @ResponseBody
    public ResponseEntity<String> addProductToCart( @RequestBody Cart cart) {
        try {
            // Kiểm tra xem có đủ hàng trong kho không
            if (isProductInStock(cart.getProductId(), cart.getQuantity())) {
                cartService.addCart(cart);
                return successResponse("Add successfully", null);
            } else {
                return errorResponse("Product is out of stock.");
            }
        } catch (Exception e) {
            return errorResponse(e.getMessage());
        }
    }
    private boolean isProductInStock(int productId, int requestedQuantity) {
        Optional<WareHouse> wareHouseOptional = wareHouseRepository.findById(productId);
        if (wareHouseOptional.isPresent()) {
            WareHouse wareHouse = wareHouseOptional.get();
            if (wareHouse.getQuantity() >= requestedQuantity) {
                return true;
            }
        }
        return false;
    }
}
