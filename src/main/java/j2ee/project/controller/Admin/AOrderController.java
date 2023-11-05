package j2ee.project.controller.Admin;

import j2ee.project.controller.Controller;
import j2ee.project.models.Order;
import j2ee.project.repository.CartRepository;
import j2ee.project.repository.OrderRepository;
import j2ee.project.service.CartService;
import j2ee.project.service.OrderDetailsService;
import j2ee.project.service.OrderService;
import j2ee.project.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/order")
public class AOrderController extends Controller {
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

    private OrderRepository orderRepository;

    @GetMapping("")
    @ResponseBody
    public ResponseEntity<String> getAllOrder(@RequestParam(defaultValue = "1") int page,
                                              @RequestParam(defaultValue = "10") int size,
                                              @RequestParam(defaultValue = "id") String sortBy,
                                              @RequestParam(defaultValue = "ASC") String sortType,
                                              @RequestParam(defaultValue = "") String name,
                                              @RequestParam(defaultValue = "") Integer status
                                              ) {
        try {
            HashMap<String, String> filters = new HashMap<>();
            if (!name.isEmpty()) {
                filters.put("name", name);
            }
            if (status != null) {
                filters.put("status", status.toString());
            }

            List<Order> listManufacture = orderService.getAllSort(page, size, sortBy, sortType,filters);
            //foreach to set image path
            //meta data
            long totalItems = orderService.count(filters);
            Map<String, Object> metaData = buildPage(totalItems, page, size);
            return this.successResponse("Get all orders.js successfully", listManufacture, metaData);
        } catch (Exception e) {
            return errorResponse(e.getMessage());
        }
    }

    @PostMapping("/{id}/cancel")
    @ResponseBody
    public ResponseEntity<String> cancelOrder(@PathVariable("id") Integer id) {
        try {
            Order order = orderRepository.findById(id).orElse(null);
            if (order == null) {
                return errorResponse("Order not found");
            }
            if (order.getStatus() != 0) {
                return errorResponse("Order can not be canceled");
            }
            order.setStatus(3);
            orderRepository.save(order);
            return successResponse("Cancel order successfully", order);
        } catch (Exception e) {
            return errorResponse(e.getMessage());
        }
    }

    @PostMapping("/{id}/confirm")
    @ResponseBody
    public ResponseEntity<String> confirmOrder(@PathVariable("id") Integer id) {
        try {
            Order order =orderRepository.findById(id).orElse(null);
            if (order == null) {
                return errorResponse("Order not found");
            }
            if (order.getStatus() != 0) {
                return errorResponse("Order can not be confirmed");
            }
            order.setStatus(1);
            orderRepository.save(order);
            return successResponse("Confirm order successfully", order);
        } catch (Exception e) {
            return errorResponse(e.getMessage());
        }
    }




}
