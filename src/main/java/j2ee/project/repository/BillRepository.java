package j2ee.project.repository;

import j2ee.project.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BillRepository extends JpaRepository<Order, Integer> {
}
