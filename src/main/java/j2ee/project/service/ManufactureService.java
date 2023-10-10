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

    public long countAllManufacture(){
        return manufactureRepository.count();
    }

    @Value("${upload.path}")
    private String uploadDirectory; // Specify your upload directory

    public String saveImage(MultipartFile imageFile) throws IOException {
        if (imageFile != null && !imageFile.isEmpty()) {
            // Generate a unique file name (you may use a more robust approach)
            String fileName = System.currentTimeMillis() + "_" + imageFile.getOriginalFilename();
            //trim
            fileName = fileName.replaceAll("\\s+", "");
            // eample have static/assets i wanna remove static

            // Ensure the upload directory exists
            File directory = new File(uploadDirectory);
            if (!directory.exists()) {
                directory.mkdirs(); // Create the directory if it doesn't exist
            }

            // Define the path where the file will be saved
            Path imagePath = Path.of(uploadDirectory, fileName);

            // Save the image file
            Files.copy(imageFile.getInputStream(), imagePath, StandardCopyOption.REPLACE_EXISTING);

            // Return the file path
            //remove /static
            String imagePathString;
            //remove /static
            imagePathString = imagePath.toString().replace("static\\", "");

            return imagePathString;
        } else {
            return null; // Return null if no image was provided
        }
    }


}
