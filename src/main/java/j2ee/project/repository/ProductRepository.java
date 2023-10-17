package j2ee.project.repository;

import j2ee.project.models.Category;
import j2ee.project.models.Product;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    List<Product> findAll(Sort sort);

    default List<Product> findWithOrder(String sortBy, String sortType) {
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

    List<Product> findByCategory(Category category);
}
