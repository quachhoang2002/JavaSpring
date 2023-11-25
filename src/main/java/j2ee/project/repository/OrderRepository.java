package j2ee.project.repository;

import j2ee.project.models.Order;
import j2ee.project.models.Product;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

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

    //find best top customer
    @Query(value = "SELECT u.id, u.name, SUM(o.total_price) AS total_purchase " +
            "FROM orders o " +
            "JOIN users u ON o.user_id = u.id " +
            "WHERE o.status = 2 " +
            "GROUP BY u.id " +
            "ORDER BY total_purchase DESC " +
            "LIMIT 10",
            nativeQuery = true)
    List<Object[]> findTopPurchasingUsers();


    @Query(value = "SELECT u.id, u.name, SUM(o.total_price) AS total_sales " +
            "FROM orders o " +
            "JOIN admin u ON o.employeeid = u.id " + // Use the correct casting
            "WHERE o.employeeid IS NOT NULL " +
            "GROUP BY u.id, u.name " +
            "ORDER BY total_sales DESC " +
            "LIMIT 10",
            nativeQuery = true)
    List<Object[]> findTopEmployeeSales();

    //get total profit
    @Query(value = "SELECT SUM(total_price)" +
            "FROM orders " +
            "WHERE status = 2", nativeQuery = true)
    Long getTotalProfit();

    //get total orders
    @Query(value = "SELECT COUNT(*) FROM orders", nativeQuery = true)
    Long getTotalOrders();

    //findProfitByYear
    @Query(value = "SELECT EXTRACT(MONTH FROM updated_at) AS month, SUM(total_price) AS total_profit " +
            "FROM orders " +
            "WHERE status = 2 AND  EXTRACT(YEAR FROM updated_at) = ?1 " +
            "GROUP BY EXTRACT(MONTH FROM updated_at) " +
            "ORDER BY EXTRACT(MONTH FROM updated_at) ASC; ",
            nativeQuery = true)
    List<Object[]> findTotalProfitByYear(int year);


}
