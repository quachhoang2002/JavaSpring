package j2ee.project.repository;

import j2ee.project.models.Order;
import j2ee.project.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository  extends JpaRepository<Order, Integer> {
    List<Order> findByUser_Id(int user_id);
}
