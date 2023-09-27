package j2ee.project.service;

import j2ee.project.models.Category;
import j2ee.project.models.Contact;
import j2ee.project.repository.ContactRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ContactService {
    private final ContactRepository contactRepository;
    private List<Contact> contacts = new ArrayList<>();

    public ContactService(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }
    public List<Contact> searchContact(@PathVariable String name){
        return contactRepository.findByNameContainingIgnoreCase(name);
    }

}
