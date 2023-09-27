package j2ee.project.repository;

import j2ee.project.models.Manufacture;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ManufactureRepository extends JpaRepository<Manufacture, Integer> {
}
