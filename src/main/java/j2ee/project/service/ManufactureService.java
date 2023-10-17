package j2ee.project.service;

import j2ee.project.models.Manufacture;
import j2ee.project.repository.ManufactureRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;

@Service
public class ManufactureService extends BaseService {
    private final ManufactureRepository manufactureRepository;

    @Autowired
    public ManufactureService(ManufactureRepository manufactureRepository) {
        this.manufactureRepository = manufactureRepository;
    }

    // CREATE
    public Manufacture addManufactory(Manufacture manufacture) {
        return manufactureRepository.save(manufacture);
    }

    // READ - Get all Manufactories with pagination
    public List<Manufacture> getAllManufacture(int page, long limit, String sortBy, String sortType) {
        //get and sort by id
        return manufactureRepository.findWithOrder(sortBy, sortType).stream().skip((page - 1) * limit).limit(limit).toList();
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
            manufacture.setAddress(updatedManufacture.getAddress());
            manufacture.setPhone(updatedManufacture.getPhone());
            manufacture.setImagePath(updatedManufacture.getImagePath());


            return manufactureRepository.save(manufacture);
        } else {
            throw new RuntimeException("Manufactory not found with id: " + id);
        }
    }

    // DELETE
    public void deleteManufacture(Integer id) {
        manufactureRepository.deleteById(id);
    }

    public long countAllManufacture() {
        return manufactureRepository.count();
    }


}
