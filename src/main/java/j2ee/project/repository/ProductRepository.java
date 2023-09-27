package j2ee.project.repository;

import j2ee.project.models.Product;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    @Modifying
    @Transactional
    @Query("DELETE FROM Product p WHERE p.category_id = :categoryId")
    void deleteByCategoryId(@Param("categoryId") int categoryId);
}
