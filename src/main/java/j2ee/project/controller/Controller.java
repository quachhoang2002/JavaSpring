package j2ee.project.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class Controller {
    protected ResponseEntity<String> successResponse(String message, Object data) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonData = objectMapper.writeValueAsString(data);
            String jsonResponse = "{\"status\": \"success\", \"message\": \"" + message + "\", \"data\": " + jsonData + "}";
            return ResponseEntity.status(HttpStatus.OK).body(jsonResponse);
        } catch (Exception e) {
            return errorResponse(e.getMessage());
        }
    }

    protected ResponseEntity<String> successResponse(String message, Object data, Map<String, Object> meta) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonData = objectMapper.writeValueAsString(data);
            String jsonMeta = objectMapper.writeValueAsString(meta);
            String jsonResponse = "{\"status\": \"success\", \"message\": \"" + message + "\", \"data\": " + jsonData + ", \"meta\": " + jsonMeta + "}";
            return ResponseEntity.status(HttpStatus.OK).body(jsonResponse);
        } catch (Exception e) {
            return errorResponse(e.getMessage());
        }
    }

    protected ResponseEntity<String> errorResponse(String message) {
        String jsonResponse = "{\"status\": \"error\", \"message\": \"" + message + "\" , \"data\": null}";
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(jsonResponse);
    }

    protected Map<String, Object> buildPage(long totalItem, int page, int size) {
        Map<String, Object> pageMap = new HashMap<String, Object>();
        int totalPage = (int) Math.ceil((double) totalItem / size);
        pageMap.put("currentPage", page);
        pageMap.put("limit", size);
        pageMap.put("totalPage", totalPage);
        pageMap.put("totalItem", totalItem);
        return pageMap;
    }


    @Value("${upload.path}")
    private String uploadDirectory; // Specify your upload directory

    public String buildImagePath(MultipartFile imageFile, String from) throws IOException {
        String uploadDirectory = this.uploadDirectory + "\\" + from;
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
