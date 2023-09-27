package j2ee.project.repository;

import j2ee.project.models.Contact;
import j2ee.project.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ContactRepository extends JpaRepository<Contact, Integer> {
    @Query("SELECT c FROM Contact c WHERE (c.name) LIKE %:name%")
    List<Contact> findByNameContainingIgnoreCase(@Param("name") String name);

}
