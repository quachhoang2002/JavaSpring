package j2ee.project.repository;

import j2ee.project.models.Cart;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {
    @Query("SELECT c FROM Cart c WHERE c.productId = :productId")
    Optional<Cart> findByProductId(@Param("productId") int productId);

    @Query("SELECT c FROM Cart c WHERE c.user_id = :user_id")
    List<Cart> findByUserId(@Param("user_id") int user_id);

    @Transactional
    @Modifying
    @Query("DELETE FROM Cart c WHERE c.productId = :productId AND c.user_id = :userId")
    void deleteByProductIdAndUserId(@Param("productId") int productId, @Param("userId") int userId);


}
