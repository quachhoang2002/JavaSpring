package j2ee.project.repository;

import j2ee.project.models.OrderDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BillDetailsRepository extends JpaRepository<OrderDetails, Integer> {
}
