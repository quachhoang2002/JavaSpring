package j2ee.project.repository;

import j2ee.project.models.WareHouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.yaml.snakeyaml.events.Event;

import java.util.Optional;

@Repository
public interface WareHouseRepository extends JpaRepository<WareHouse, Integer> {
    Optional<WareHouse> findByProductId(int productId);
}
