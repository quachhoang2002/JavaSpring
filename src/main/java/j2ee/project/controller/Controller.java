package j2ee.project.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class Controller {

    protected ResponseEntity<String> successResponse(String message, Object data) {
        String jsonResponse = "{\"status\": \"success\", \"message\": \"" + message + "\", \"data\": " + data + "}";
        return ResponseEntity.status(HttpStatus.OK).body(jsonResponse);
    }

    protected ResponseEntity<String> errorResponse(String message) {
        String jsonResponse = "{\"status\": \"error\", \"message\": \"" + message + "\" , \"data\": null}";
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(jsonResponse);
    }


}
