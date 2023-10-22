package j2ee.project.repository;

import j2ee.project.models.Product;
import j2ee.project.models.Stock;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StockRepository extends JpaRepository<Stock, Integer> {
    Optional<Stock> findByProduct(Product product);

    default List<Stock> findWithOrder(String sortBy, String sortType) {
        if (sortBy == null || sortBy.equals("")) {
            sortBy = "id";
        }
        if (sortType == null || sortType.equals("")) {
            sortType = "ASC";
        }
        Sort.Direction direction = Sort.Direction.fromString(sortType);
        Sort sort = Sort.by(direction, sortBy);
        return findAll(sort);
    }

}
