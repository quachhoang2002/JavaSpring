package j2ee.project.repository;

import j2ee.project.models.Order;
import j2ee.project.models.Product;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository  extends JpaRepository<Order, Integer> {
    List<Order> findByUser_Id(int user_id);


    default List<Order> findWithOrder(String sortBy, String sortType) {
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
