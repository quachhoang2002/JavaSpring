package j2ee.project.service;

import j2ee.project.models.Manufacture;
import j2ee.project.models.Order;
import j2ee.project.models.Product;
import j2ee.project.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    public Order createOrder(Order order) {
        orderRepository.save(order);
        return order;
    }

    public void updateOrder(Order order) {
        orderRepository.save(order);
    }

    public Order getOrderById(int id) {
        return orderRepository.findById(id).orElse(null);
    }

    public List<Order> getAllOrderByUserId(int user_Id) {
        return orderRepository.findByUser_Id(user_Id);
    }

    public List<Order> getAllSort(int page, int limit, String sortBy, String sortType, Map<String, String> filters) {
        //get and sort by id
        Stream<Order> products = orderRepository.findWithOrder(sortBy, sortType).stream();
        products = buildFilter(products, filters, page, limit);
        return products.toList();
    }

    public long count(Map<String, String> filters) {
        Stream<Order> products = orderRepository.findAll().stream();
        products = buildFilter(products, filters, 0, 0);

        return products.count();
    }

    public Stream<Order> buildFilter(Stream<Order> products, Map<String, String> filters, int page, int limit) {
        for (Map.Entry<String, String> entry : filters.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();

            if (key.equals("name")) {
                products = products.filter(product ->
                        product.getCustomerName().toLowerCase().contains(value.toLowerCase()) ||
                                product.getCustomerPhone().toLowerCase().contains(value.toLowerCase())
                );
            }

            if (key.equals("status")) {
                //status
                Integer status = Integer.parseInt(value);
                products = products.filter(product -> product.getStatus() == status);
            }
        }

        if (page != 0 && limit != 0) {
            products = products.skip((page - 1) * limit).limit(limit);
        }

        return products;
    }

}
