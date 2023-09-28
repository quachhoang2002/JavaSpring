package j2ee.project.repository;

import j2ee.project.models.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


public interface CartRepository extends JpaRepository<Cart, Integer> {
}
