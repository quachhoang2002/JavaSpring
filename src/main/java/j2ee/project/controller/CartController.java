package j2ee.project.controller;

import j2ee.project.models.Cart;
import j2ee.project.models.Product;
import j2ee.project.repository.CartRepository;
import jakarta.persistence.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart/")
public class CartController extends Controller{
    @Autowired
    private CartRepository cartRepository; // CartRepository là một repository để truy vấn và lưu trữ dữ liệu giỏ hàng
    @PostMapping("/add")
    public ResponseEntity<String> addToCart(@RequestBody Cart cart) {
        if (cart != null && cart.getProduct() != null && cart.getQuantity() > 0) {
            cartRepository.save(cart);
            return ResponseEntity.ok("Sản phẩm đã được thêm vào giỏ hàng.");
        } else {
            return ResponseEntity.badRequest().body("Dữ liệu không hợp lệ.");
        }
    }

    @DeleteMapping("/delete/{id}")
    @ResponseBody
    public ResponseEntity<String> deleteCategory(@PathVariable int id) {
        try {
            cartRepository.deleteById(id);
            return successResponse("Delete successfully", null);
        } catch (Exception e) {
            return errorResponse(e.getMessage());
        }
    }
    @GetMapping("/getAll")
    public List<Cart> getAllCartItems() {
        return cartRepository.findAll();
    }
}
