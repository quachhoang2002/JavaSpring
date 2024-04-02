package j2ee.project.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class BaseService {
    @Value("${upload.path}")
    private String uploadDirectory; // Specify your upload directory

    public String buildImagePath(MultipartFile imageFile, String from) throws IOException {
        String uploadDirectory = this.uploadDirectory;
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
//            imagePathString = imagePath.toString().replace("static\\", "");

            //add http://t.hoangdeptrai.online to prefix
            imagePathString = "https://t.hoangdeptrai.online/" + imagePath.toString().replace("static\\", "");


            return imagePathString;
        } else {
            return null; // Return null if no image was provided
        }
    }
}
