package j2ee.project.service;

import j2ee.project.models.OrderDetails;
import j2ee.project.repository.OrderDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderDetailsService {
    @Autowired
    private OrderDetailsRepository orderDetailsRepository;
    public OrderDetails createOrderDetails(OrderDetails orderDetails){
        orderDetailsRepository.save(orderDetails);
        return orderDetails;
    }
}
