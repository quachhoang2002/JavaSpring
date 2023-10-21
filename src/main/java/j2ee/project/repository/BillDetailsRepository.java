package j2ee.project.repository;

import j2ee.project.models.BillDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BillDetailsRepository extends JpaRepository<BillDetails, Integer> {
}
