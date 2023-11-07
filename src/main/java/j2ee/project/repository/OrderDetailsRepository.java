package j2ee.project.repository;

import j2ee.project.models.OrderDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderDetailsRepository extends JpaRepository<OrderDetails, Integer> {

    Optional<OrderDetails> findByOrder(int orderid);
}
