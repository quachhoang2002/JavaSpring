package j2ee.project.controller.User;

import j2ee.project.controller.Controller;
import j2ee.project.models.*;
import j2ee.project.repository.*;
import j2ee.project.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductService productService;
    @PostMapping("/add")
    @ResponseBody
    public Order createOrder(List<Cart> cartItems) {
        // Tạo đối tượng Order và gán thông tin từ các đối tượng Cart
        Order order = new Order();
        order.setCustomerName("Customer Name"); // Thay bằng tên khách hàng
        order.setShippingAddress("Shipping Address"); // Thay bằng địa chỉ giao hàng
        order.setCustomerPhone("Customer Phone"); // Thay bằng số điện thoại khách hàng
        order.setEmail_receive("Email Address"); // Thay bằng địa chỉ email
        order.setTotal_price(calculateTotalPrice(cartItems)); // Tính tổng giá trị đơn hàng

        // Tạo danh sách OrderDetails từ danh sách Cart
        List<OrderDetails> orderDetailsList = cartItems.stream()
                .map(cartItem -> {
                    OrderDetails orderDetails = new OrderDetails();
                    orderDetails.setOrder(order);
                    Optional<Product> product = getProductFromCart(cartItem);
                    orderDetails.setProduct(product);
                    orderDetails.setQuantity(cartItem.getQuantity());
                    orderDetails.setPrice(cartItem.getPrice());
                    return orderDetails;
                })
                .collect(Collectors.toList());

        // Gán danh sách OrderDetails vào đối tượng Order
        order.setOrderDetails(orderDetailsList);

        return order;
    }

    // Hàm tính tổng giá trị đơn hàng từ danh sách Cart
    private double calculateTotalPrice(List<Cart> cartItems) {
        return cartItems.stream()
                .mapToDouble(cartItem -> cartItem.getPrice() * cartItem.getQuantity())
                .sum();
    }

    // Hàm lấy thông tin sản phẩm từ đối tượng Cart
    private Optional<Product> getProductFromCart(Cart cartItem) {
        // Thực hiện truy vấn cơ sở dữ liệu hoặc logic để lấy thông tin sản phẩm dựa trên cartItem.productId
        // Ví dụ:
        Optional<Product> product = productService.getProductById(cartItem.getProductId());
        return product;
    }

}
