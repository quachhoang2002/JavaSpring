package j2ee.project.controller.User;

import j2ee.project.controller.Controller;
import j2ee.project.models.*;
import j2ee.project.repository.CartRepository;
import j2ee.project.repository.ProductRepository;
import j2ee.project.repository.WareHouseRepository;
import j2ee.project.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/cart/")
public class CartController extends Controller {
    @Autowired
    private CartService cartService;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private WareHouseRepository wareHouseRepository;
    @PostMapping("/add")
    @ResponseBody
    public ResponseEntity<String> addProductToCart(@RequestBody Cart cart) {
        try {
            int productId = cart.getProductId();
            int quantity = cart.getQuantity();

            // Check if the product with the given productId already exists in the cart
            Optional<Cart> existingCartItem = cartService.findCartByProductId(productId);

            if (existingCartItem.isPresent()) {
                // Product already exists in the cart, update the quantity
                Cart cartItem = existingCartItem.get();
                cartItem.setQuantity(cartItem.getQuantity() + quantity);
                cartService.updateCart(cartItem);
            } else {
                // Product doesn't exist in the cart, add a new row
                // Kiểm tra xem có đủ hàng trong kho không
                if (isProductInStock(productId, quantity)) {
                    cartService.addCart(cart);
                } else {
                    return errorResponse("Product is out of stock.");
                }
            }

            return successResponse("Add successfully", null);
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
    @GetMapping("/getAll")
    public ResponseEntity<?> getAll() {
        List<Cart> cart = cartRepository.findAll();
        List<Product> productsInCart = new ArrayList<>();

        if (cart != null && !cart.isEmpty()) {
            for (Cart cartItem : cart) {
                Optional<Product> product = productRepository.findById(cartItem.getProductId());
                product.ifPresent(productsInCart::add);
            }

            if (!productsInCart.isEmpty()) {
                Map<String, Object> response = new HashMap<>();
                response.put("cartItems", cart);
                response.put("productItems", productsInCart);
                return ResponseEntity.ok(response);
            } else {
                return errorResponse("Products not found in the cart");
            }
        } else {
            return errorResponse("Cart is empty");
        }
    }
    @PutMapping("/updateQuantity")
    public ResponseEntity<String> updateQuantity(@RequestBody Map<String, Object> request) {
        try {
            int productId = (int) request.get("id"); // Lấy productId từ request
            int newQuantity = (int) request.get("quantity"); // Lấy quantity từ request

            if (!isProductInStock(productId, newQuantity)) {
                return errorResponse("Product is out of stock.");
            }

            Optional<Cart> existingCartItem = cartService.findCartByProductId(productId);
            if (existingCartItem.isPresent()) {
                Cart cartItem = existingCartItem.get();
                cartItem.setQuantity(newQuantity);
                cartService.updateCart(cartItem);
                return successResponse("Update successfully", null);
            } else {
                return errorResponse("Cart item not found.");
            }
        } catch (Exception e) {
            return errorResponse(e.getMessage());
        }
    }


}
