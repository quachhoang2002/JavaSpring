package j2ee.project.controller;

import j2ee.project.POJO.Cart;
import j2ee.project.POJO.CartItem;
import j2ee.project.POJO.CustomerInfo;
import j2ee.project.models.*;
import j2ee.project.repository.BillDetailsRepository;
import j2ee.project.repository.BillRepository;
import j2ee.project.service.ProductService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/cart/")
public class CartController extends Controller{
    @Autowired
    private HttpSession session;

    @Autowired
    private BillRepository billRepository; // BillRepository là một JpaRepository cho đơn hàng

    @Autowired
    private BillDetailsRepository billDetailRepository; // BillDetailRepository là một JpaRepository cho chi tiết đơn hàng
    @Autowired
    private ProductService productService;
    // Thêm sản phẩm vào giỏ hàng và lưu vào session
    @PostMapping("/add")
    public ResponseEntity<String> addToCart(@RequestBody Map<String, Object> request) {
        Integer productId = (Integer) request.get("productId");
        Integer quantity = (Integer) request.get("quantity");
        Double price = (Double) request.get("price");
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart == null) {
            cart = new Cart();
            session.setAttribute("cart", cart);
        }

        // Thêm sản phẩm vào giỏ hàng
        cart.addProduct(productId, quantity, price);

        // Lưu giỏ hàng vào session
        session.setAttribute("cart", cart);

        return ResponseEntity.ok("Sản phẩm đã được thêm vào giỏ hàng.");
    }

    // Hoàn tất đơn hàng
    @PostMapping("/checkout")
    public ResponseEntity<String> checkout(@RequestBody CustomerInfo customerInfo) {
        // Lấy giỏ hàng từ session
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart == null || cart.isEmpty()) {
            return ResponseEntity.badRequest().body("Giỏ hàng trống.");
        }

        // Tạo một đơn hàng (bill) và thiết lập các thông tin mới từ DTO
        Bill bill = new Bill();
        bill.setCustomerName(customerInfo.getCustomerName());
        bill.setShippingAddress(customerInfo.getShippingAddress());
        bill.setCustomerPhone(customerInfo.getCustomerPhone());
        bill.setStatus(customerInfo.getStatus());

        billRepository.save(bill);

        // Lấy danh sách sản phẩm từ giỏ hàng và tạo chi tiết đơn hàng (bill details)
        for (CartItem item : cart.getItems()) {
            Optional<Product> product = productService.getProductById(item.getProductId());

            BillDetails billDetail = new BillDetails();
            billDetail.setBillId(bill.getId());
            billDetail.setProductId(product.get().getId());
            billDetail.setQuantity(item.getQuantity());
            billDetail.setPrice(product.get().getPrice());

            billDetailRepository.save(billDetail);
        }

        // Xóa giỏ hàng sau khi đã tạo đơn hàng
        session.removeAttribute("cart");

        return ResponseEntity.ok("Đơn hàng đã được tạo thành công.");
    }



}
