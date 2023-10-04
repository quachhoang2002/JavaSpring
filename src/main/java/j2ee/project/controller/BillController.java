package j2ee.project.controller;

import j2ee.project.models.*;
import j2ee.project.repository.BillDetailsRepository;
import j2ee.project.repository.BillRepository;
import j2ee.project.service.ProductService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/bill/")
public class BillController extends Controller{

//    @Autowired
//    private HttpSession session;
//
//    @Autowired
//    private BillRepository billRepository; // BillRepository là một JpaRepository cho đơn hàng
//
//    @Autowired
//    private BillDetailsRepository billDetailRepository; // BillDetailRepository là một JpaRepository cho chi tiết đơn hàng
//    @Autowired
//    private ProductService productService;
//    // Thêm sản phẩm vào giỏ hàng và lưu vào session
//    @PostMapping("/add")
//    public ResponseEntity<String> addToCart(@RequestParam int productId, @RequestParam int quantity, @RequestParam double price) {
//        // Lấy giỏ hàng từ session hoặc tạo mới nếu chưa tồn tại
//        Cart cart = (Cart) session.getAttribute("cart");
//        if (cart == null) {
//            cart = new Cart();
//            session.setAttribute("cart", cart);
//        }
//
//        // Thêm sản phẩm vào giỏ hàng
//        cart.addProduct(productId, quantity, price);
//
//        // Lưu giỏ hàng vào session
//        session.setAttribute("cart", cart);
//
//        return ResponseEntity.ok("Sản phẩm đã được thêm vào giỏ hàng.");
//    }
//
//    // Hoàn tất đơn hàng
//    @PostMapping("/checkout")
//    public ResponseEntity<String> checkout() {
//        // Lấy giỏ hàng từ session
//        Cart cart = (Cart) session.getAttribute("cart");
//        if (cart == null || cart.isEmpty()) {
//            return ResponseEntity.badRequest().body("Giỏ hàng trống.");
//        }
//
//        // Tạo một đơn hàng (bill) và lưu vào cơ sở dữ liệu
//        Bill bill = new Bill();
//        billRepository.save(bill);
//
//        // Lấy danh sách sản phẩm từ giỏ hàng và tạo chi tiết đơn hàng (bill details)
//        for (CartItem item : cart.getItems()) {
//
//            Optional<Product> product = productService.getProductById(item.getProductId());
//
//            BillDetails billDetail = new BillDetails();
//            billDetail.setBillId(bill.getId());
//            billDetail.setProductId(product.get().getId());
//            billDetail.setQuantity(item.getQuantity());
//            billDetail.setPrice(product.get().getPrice());
//
//            billDetailRepository.save(billDetail);
//        }
//
//        // Xóa giỏ hàng sau khi đã tạo đơn hàng
//        session.removeAttribute("cart");
//
//        return ResponseEntity.ok("Đơn hàng đã được tạo thành công.");
//    }
}
