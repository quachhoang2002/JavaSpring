package j2ee.project.controller;

import j2ee.project.service.HitCounterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/hit-counter/")
public class HitCounter {

    @Autowired
    private HitCounterService hitCounterService;
    @GetMapping("/")
    public String init() throws IOException {

        return "Hit count: " + String.valueOf(hitCounterService.init());
    }
    @GetMapping("/hit")
    public String hit() {
        hitCounterService.incrementHitCount();
        return "Hit count: " + hitCounterService.getHitCount();
    }

    @GetMapping("/save")
    public String save() throws IOException {
        hitCounterService.saveHitCount();
        return "Hit count saved.";
    }
    @GetMapping("/destroy")
    public String destroy() throws IOException {
        hitCounterService.destroy();
        return "Hit count was destroyed.";
    }

}
