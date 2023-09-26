package j2ee.project.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class Controller {

    protected ResponseEntity<String> successResponse(String message, Object data) {
        try{
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonData = objectMapper.writeValueAsString(data);
            String jsonResponse = "{\"status\": \"success\", \"message\": \"" + message + "\", \"data\": " + jsonData + "}";
            return ResponseEntity.status(HttpStatus.OK).body(jsonResponse);
        }catch (Exception e){
            return errorResponse(e.getMessage());
        }
    }

    protected ResponseEntity<String> errorResponse(String message) {
        String jsonResponse = "{\"status\": \"error\", \"message\": \"" + message + "\" , \"data\": null}";
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(jsonResponse);
    }


}
