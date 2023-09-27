package j2ee.project.controller;

import j2ee.project.models.Contact;
import j2ee.project.repository.ContactRepository;
import j2ee.project.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contact/")
public class ContactController extends Controller{
    @Autowired
    private final ContactRepository contactRepository;
    @Autowired
    private final ContactService contactService;

    public ContactController(ContactRepository contactRepository, ContactService contactService) {
        this.contactRepository = contactRepository;
        this.contactService = contactService;
    }
    @PostMapping("/add")
    @ResponseBody
    public ResponseEntity<String> register(@RequestBody Contact contact) {
        try {
            contactRepository.save(contact);
            return successResponse("Add successfully", null);

        } catch (Exception e) {
            return errorResponse(e.getMessage());
        }
    }
    @GetMapping("/search/{name}")
    @ResponseBody
    public List<Contact> searchContact(@PathVariable String name) {
        return contactService.searchContact(name);
    }
}
