package j2ee.project.repository;

import j2ee.project.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Integer> {

    @Query("SELECT u FROM User u WHERE u.email = ?1 ")
    User findByEmail(String email);

    @Query("SELECT u FROM User u WHERE u.token = ?1 ")
    User findByToken(String token);


}