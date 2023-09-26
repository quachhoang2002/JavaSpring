package j2ee.project.repository;

import j2ee.project.models.Manufactory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ManufactoryRepository extends JpaRepository<Manufactory, Integer> {
}
