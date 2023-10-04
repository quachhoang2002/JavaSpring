package j2ee.project.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Controller
public class FileUploadController {

    @Value("${file.upload-dir}")
    private String uploadDir; // This should point to your actual directory path

    // Mapping for the file upload form
//    @GetMapping("/upload")
//    public String showUploadForm() {
//        return "upload/form"; // You can create an HTML form for file upload
//    }

    // Modify the uploadFiles method to use a single method for generating unique file names
    @PostMapping("/upload")
    @ResponseBody
    public String uploadFiles(
            @RequestParam("imageFile") MultipartFile imageFile,
            @RequestParam("documentFile") MultipartFile documentFile,
            Model model
    ) {
        String imageFileName = generateUniqueFileName(imageFile.getOriginalFilename());
        String documentFileName = generateUniqueFileName(documentFile.getOriginalFilename());

        String responseMessage = validateAndUploadFiles(imageFile, documentFile, imageFileName, documentFileName);

        if (responseMessage == null) {
            return "Files uploaded successfully! Image: " + imageFileName + ", Document: " + documentFileName;
        } else {
            return "Error uploading files: " + responseMessage;
        }
    }

    // Modify the validateAndUploadFiles method to accept file names as parameters
    private String validateAndUploadFiles(
            MultipartFile imageFile, MultipartFile documentFile,
            String imageFileName, String documentFileName
    ) {
        if (imageFile.isEmpty() || documentFile.isEmpty()) {
            return "Please select both an image and a document file.";
        }

        try {
            imageFile.transferTo(new File(uploadDir + File.separator + imageFileName));
            documentFile.transferTo(new File(uploadDir + File.separator + documentFileName));

            return null; // No error, successful upload
        } catch (IOException e) {
            e.printStackTrace(); // Log the exception
            return e.getMessage();
        }
    }

    // No changes to the generateUniqueFileName method
    private String generateUniqueFileName(String originalFileName) {
        return UUID.randomUUID() + "_" + originalFileName;
    }
    // Configuration for serving static resources (images)
    @Configuration
    public class WebConfig implements WebMvcConfigurer {

        @Override
        public void addResourceHandlers(ResourceHandlerRegistry registry) {
            registry.addResourceHandler("/assets/images/**")
                    .addResourceLocations("file:" + uploadDir + File.separator );
        }
    }
}
