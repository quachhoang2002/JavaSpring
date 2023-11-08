package j2ee.project.service;

import j2ee.project.models.Order;
import j2ee.project.models.OrderDetails;
import j2ee.project.repository.OrderDetailsRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderDetailsService {
    @Autowired
    private OrderDetailsRepository orderDetailsRepository;
    public OrderDetails createOrderDetails(OrderDetails orderDetails){
        orderDetailsRepository.save(orderDetails);
        return orderDetails;
    }

    @Transactional
    public List<OrderDetails> getOrderDetailsByOrder(Order order) {
        return orderDetailsRepository.findByOrder(order);
    }
}
