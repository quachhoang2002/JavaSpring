package j2ee.project.service;

import j2ee.project.models.Manufacture;
import j2ee.project.repository.ManufactureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ManufactureService {
    private final ManufactureRepository manufactureRepository;

    @Autowired
    public ManufactureService(ManufactureRepository manufactureRepository) {
        this.manufactureRepository = manufactureRepository;
    }

    // CREATE
    public Manufacture addManufactory(Manufacture manufacture) {
        return manufactureRepository.save(manufacture);
    }

    // READ - Get all Manufactories
    public List<Manufacture> getAllManufacture() {
        return manufactureRepository.findAll();
    }

    // READ - Get a Manufactory by ID
    public Optional<Manufacture> getManufactureById(Integer id) {
        return manufactureRepository.findById(id);
    }

    // UPDATE
    public Manufacture updateManufacture(Integer id, Manufacture updatedManufacture) {
        Optional<Manufacture> existingManufactory = manufactureRepository.findById(id);

        if (existingManufactory.isPresent()) {
            Manufacture manufacture = existingManufactory.get();
            manufacture.setName(updatedManufacture.getName());

            return manufactureRepository.save(manufacture);
        } else {
            throw new RuntimeException("Manufactory not found with id: " + id);
        }
    }

    // DELETE
    public void deleteManufacture(Integer id) {
        manufactureRepository.deleteById(id);
    }


}
