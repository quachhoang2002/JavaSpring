package j2ee.project.repository;

import j2ee.project.models.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, Integer> {
    //findByEmailAndPassword
    Admin findByEmailAndPassword(String email, String password);

    //findByEmail
    Admin findByEmail(String email);

    //findByToken
    Admin findByToken(String token);
}
