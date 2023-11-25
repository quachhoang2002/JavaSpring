package j2ee.project.repository;

import j2ee.project.models.Category;
import j2ee.project.models.Product;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

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


    //query best seller
    Optional<Product> findById(int productId);

    @Query(value = "SELECT od.product_id, SUM(od.quantity) AS total_quantity_sold, p.name " +
            "FROM order_details od " +
            "JOIN products p ON od.product_id = p.id " +
            "GROUP BY p.id, od.product_id " +
            "ORDER BY total_quantity_sold DESC " +
            "LIMIT 10",
            nativeQuery = true)
    List<Object[]> findBestSeller();

    //getTotalProducts
    @Query(value = "SELECT COUNT(*) FROM products", nativeQuery = true)
    Long getTotalProducts();
}
