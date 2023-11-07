package j2ee.project.controller.User;

import j2ee.project.controller.Controller;
import j2ee.project.models.Order;
import j2ee.project.models.OrderDetails;
import j2ee.project.service.OrderDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/orderDetails/")
public class OrderDetailsController extends Controller {
    @Autowired
    private OrderDetailsService orderDetailsService;
    @PostMapping("/add")
    public ResponseEntity<?> createOrder(@RequestBody OrderDetails orderDetails) {

        if (orderDetailsService.createOrderDetails(orderDetails) != null) {
            return successResponse("successfully", orderDetails);
        } else {
            return errorResponse("Failed");
        }
    }
    @GetMapping("/getOrderDetail/{orderid}")
    public ResponseEntity<?> getOrderDetail(@PathVariable("orderid") Order order) {
        // Sử dụng order.getId() để lấy id của order
        Optional<OrderDetails> orderDetail = orderDetailsService.findByOrder(order.getId());
        System.out.println(orderDetail);
        if (orderDetail.isPresent()) {
            return successResponse("successfully", orderDetail.get());
        } else {
            return errorResponse("Failed");
        }
    }

}
