package j2ee.project.service;

import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

@Service
public class HitCounterService {

    @Value("${count.file.path}")
    private String countFilePath;

    private int hitCount;
    private int initialHitCount;
    public int getHitCount() {
        return hitCount;
    }

    public int init() throws IOException {
        try {
            hitCount = Integer.parseInt(Files.readAllLines(Path.of(countFilePath)).get(0));
            initialHitCount = hitCount;
        } catch (IOException ex) {
            // Handle the exception
            ex.printStackTrace();
        }
        return  hitCount;
    }

    public void incrementHitCount() {
        hitCount++;
    }

    public void saveHitCount() throws IOException {
        Files.write(Path.of(countFilePath), String.valueOf(hitCount).getBytes(), StandardOpenOption.WRITE);
    }
    @PreDestroy
    public void destroy() throws IOException {
        // This method will be called when the application is shutting down
        initialHitCount = 0;
        hitCount = initialHitCount;
        saveHitCount();
        saveHitCount();
    }
}
