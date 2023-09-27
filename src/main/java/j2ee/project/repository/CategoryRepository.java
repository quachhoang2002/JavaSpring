package j2ee.project.repository;

import j2ee.project.models.Category;
import j2ee.project.models.Product;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


public interface CategoryRepository extends JpaRepository<Category, Integer> {

}
