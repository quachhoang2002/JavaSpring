package j2ee.project.controller.User;

import j2ee.project.controller.Controller;
import j2ee.project.models.*;
import j2ee.project.repository.*;
import j2ee.project.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/order/")
public class OrderController extends Controller {
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private OrderService orderService;
    @Autowired
    private CartService cartService;
    @Autowired
    private OrderDetailsService orderDetailsService;
    @Autowired
    private StockService stockService;
    @Autowired
    private EmailService emailService;
    @Autowired
    private ProductService productService;
    @PostMapping("/add")
    public ResponseEntity<?> createOrder(@RequestBody Order order) {
        Order createdOrder = orderService.createOrder(order);
        createOrderDetails(order, order.getUser().getId());
        if (createdOrder != null) {
            return successResponse("successfully", order);
        } else {
            return errorResponse("Failed");
        }
    }
    public ResponseEntity<?> createOrderDetails(Order order, int userId) {
        List<Cart> cartList = cartService.findCartByUserId(userId);
        List<Product> productList = productService.getAll();
        double totalPrice = 0;
        for (Cart cartItem : cartList) {
            int productId = cartItem.getProductId();
            int quantity = cartItem.getQuantity();
                Product product = new Product();
                product.setId(cartItem.getProductId());
                OrderDetails orderDetail = new OrderDetails();
                orderDetail.setProduct(product);
                orderDetail.setQuantity(cartItem.getQuantity());
                orderDetail.setPrice(cartItem.getPrice());
                orderDetail.setOrder(order);

                productList.add(product);
                totalPrice += cartItem.getPrice() * cartItem.getQuantity();

                stockService.decreaseStockQuantity(productId, quantity);
                orderDetailsService.createOrderDetails(orderDetail);
                cartService.deleteByProductIdAndUserId(cartItem.getProductId(), cartItem.getUser_id());
        }
        emailService.sendOrderConfirmationEmail(
                order.getEmail_receive(),
                order.getCustomerName(),
                order.getShippingAddress(),
                order.getCustomerPhone(),
                cartList,
                totalPrice
        );

        return successResponse("successfully", null);
    }

    @GetMapping("/getByUserId/{userId}")
    public ResponseEntity<?> getAllOrder(@PathVariable int userId) {
       List<Order> orderList = orderService.getAllOrderByUserId(userId);
       if(orderList != null ){
           return  successResponse("Success", orderList);
       }else {
           return  errorResponse("fail");
       }
    }
}
