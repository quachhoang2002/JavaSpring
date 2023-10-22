package j2ee.project.repository;

import j2ee.project.models.Product;
import j2ee.project.models.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WareHouseRepository extends JpaRepository<Stock, Integer> {
    Optional<Stock> findByProduct(Product product);
}
