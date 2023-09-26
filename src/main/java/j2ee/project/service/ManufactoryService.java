package j2ee.project.service;

import j2ee.project.models.Manufactory;
import j2ee.project.repository.ManufactoryRepository;
import j2ee.project.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ManufactoryService {
    private final ManufactoryRepository manufactoryRepository;

    @Autowired
    public ManufactoryService(ManufactoryRepository manufactoryRepository) {
        this.manufactoryRepository = manufactoryRepository;
    }

    // CREATE
    public Manufactory addManufactory(Manufactory manufactory) {
        return manufactoryRepository.save(manufactory);
    }

    // READ - Get all Manufactories
    public List<Manufactory> getAllManufactories() {
        return manufactoryRepository.findAll();
    }

    // READ - Get a Manufactory by ID
    public Optional<Manufactory> getManufactoryById(Integer id) {
        return manufactoryRepository.findById(id);
    }

    // UPDATE
    public Manufactory updateManufactory(Integer id, Manufactory updatedManufactory) {
        Optional<Manufactory> existingManufactory = manufactoryRepository.findById(id);

        if (existingManufactory.isPresent()) {
            Manufactory manufactory = existingManufactory.get();
            manufactory.setName(updatedManufactory.getName());
            // You can update other fields as needed

            return manufactoryRepository.save(manufactory);
        } else {
            throw new RuntimeException("Manufactory not found with id: " + id);
        }
    }

    // DELETE
    public void deleteManufactory(Integer id) {
        manufactoryRepository.deleteById(id);
    }


}
