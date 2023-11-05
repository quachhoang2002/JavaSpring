package j2ee.project.controller.User;

import j2ee.project.controller.Controller;
import j2ee.project.models.*;
import j2ee.project.repository.CartRepository;
import j2ee.project.repository.ProductRepository;
import j2ee.project.repository.StockRepository;
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
    private StockRepository stockRepository;
    @PostMapping("/add")
    @ResponseBody
    public ResponseEntity<String> addProductToCart(@RequestBody Cart cart) {
        try {
            int productId = cart.getProductId();
            int quantity = cart.getQuantity();
            int user_id = cart.getUser_id();
            // Check if the product with the given productId already exists in the cart
            Optional<Cart> existingCartItem = cartService.findCartByProductIdAndUser_Id(productId, user_id);
            Product product = productRepository.findById(productId).get();

            if (existingCartItem.isPresent()) {
                // Product already exists in the cart, update the quantity
                if (!isProductInStock(product, quantity)) {
                    return errorResponse("Product is out of stock.");
                }

                Cart cartItem = existingCartItem.get();
                cartItem.setQuantity(cartItem.getQuantity() + quantity);
                cartService.updateCart(cartItem);
            } else {
                // Product doesn't exist in the cart, add a new row
                // Kiểm tra xem có đủ hàng trong kho không
                if (isProductInStock(product, quantity)) {
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

    private boolean isProductInStock(Product product, int requestedQuantity) {
        Optional<Stock> wareHouseOptional = stockRepository.findByProduct(product);
        if (wareHouseOptional.isPresent()) {
            Stock wareHouse = wareHouseOptional.get();
            System.out.println("aasdasdsad"+wareHouse.getQuantity());
            System.out.println("aaassdfsaf"+requestedQuantity);
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
    @GetMapping("/getByUserId/{userId}")
    public ResponseEntity<?> getByUserId(@PathVariable int userId) {
        List<Cart> cart = cartRepository.findByUserId(userId);
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
            int productId = Integer.parseInt(request.get("id").toString());
            int user_id = Integer.parseInt(request.get("user_id").toString());
            int newQuantity = Integer.parseInt(request.get("quantity").toString());



            Product product = productRepository.findById(productId).get();
            System.out.println(product);

            if (!isProductInStock(product, newQuantity)) {
                return errorResponse("Product is out of stock.");
            }

            Optional<Cart> existingCartItem = cartService.findCartByProductIdAndUser_Id(productId, user_id);
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
    @DeleteMapping("/deleteItem")
    public ResponseEntity<String> deleteCartItem(@RequestBody Map<String, Object> request) {
        try {
            Integer productId = (Integer) request.get("productId");
            Integer user_id = (Integer) request.get("user_id");

            if (productId == null || user_id == null) {
                return ResponseEntity.badRequest().body("Invalid parameters. Both user_id and id must be provided.");
            }

            // Call the service to delete the product from the cart based on userId and productId
            boolean isDeleted = cartService.deleteCartItem(user_id, productId);

            if (isDeleted) {
                return successResponse("Xóa sản phẩm khỏi giỏ hàng thành công.", isDeleted);
            } else {
                return errorResponse("Thất bại");
            }
        } catch (Exception e) {
            return errorResponse("Thất bại");
        }
    }
}
