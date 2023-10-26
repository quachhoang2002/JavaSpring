package j2ee.project.controller.User;

import j2ee.project.controller.Controller;
import j2ee.project.models.*;
import j2ee.project.repository.*;
import j2ee.project.service.CartService;
import j2ee.project.service.OrderDetailsService;
import j2ee.project.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<?> createOrderDetails(Order order,  int userId){
        List<Cart> cartList = cartService.findCartByUserId(userId);
        for(Cart cartItem : cartList){
            OrderDetails orderDetail = new OrderDetails();
            Product product = new Product();
            product.setId(cartItem.getProductId());
            orderDetail.setProduct(product);
            orderDetail.setQuantity(cartItem.getQuantity());
            orderDetail.setPrice(cartItem.getPrice());
            orderDetail.setOrder(order);
            orderDetailsService.createOrderDetails(orderDetail);
            System.out.println("zxc zxczczxczxczxczxc z zxczxc" + cartItem.getProductId());
            System.out.println("zxc zxczczxczxczxczxc z zxczxc" + cartItem.getUser_id());
            cartService.deleteByProductIdAndUserId(cartItem.getProductId(), cartItem.getUser_id());
        }
        return successResponse("successfully",null);

    }
}
