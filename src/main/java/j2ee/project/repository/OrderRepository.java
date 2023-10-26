package j2ee.project.repository;

import j2ee.project.models.Order;
import j2ee.project.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository  extends JpaRepository<Order, Integer> {

}
