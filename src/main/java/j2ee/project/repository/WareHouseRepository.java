package j2ee.project.repository;

import j2ee.project.models.WareHouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.yaml.snakeyaml.events.Event;

import java.util.List;
import java.util.Optional;

@Repository
public interface WareHouseRepository extends JpaRepository<WareHouse, Integer> {
    Optional<WareHouse> findByProduct_Id(int productId);
}
