package j2ee.project.service;

import j2ee.project.models.Order;
import j2ee.project.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    public Order createOrder(Order order){
        orderRepository.save(order);
        return order;
    }
}
