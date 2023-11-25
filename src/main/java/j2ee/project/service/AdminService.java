package j2ee.project.service;

import j2ee.project.models.Admin;
import j2ee.project.models.Product;
import j2ee.project.repository.AdminRepository;
import j2ee.project.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Service
public class AdminService extends BaseService {
    private final AdminRepository adminRepository;


    @Autowired
    public AdminService(AdminRepository adminRepository, ProductRepository productRepository) {
        this.adminRepository = adminRepository;
    }

    //add category
    public Admin addAdmin(Admin category) {
        return adminRepository.save(category);
    }

    //get all categories
    // READ - Get all Manufactories with pagination
    public List<Admin> getAll(int page, long limit, String name) {
        //get and sort by id
        Stream<Admin> data = adminRepository.findAll().stream();
        data = data.filter(product -> product.getName().toLowerCase().contains(name.toLowerCase()));
        //sort by role
        data = data.sorted(Comparator.comparing(Admin::getRole));

        return data.skip((page - 1) * limit).limit(limit).toList();
    }
    
    public List<Admin> getAll(){
        return adminRepository.findAll();
    }
    //get category by id
    public Optional<Admin> getAdminById(Integer id) {
        return adminRepository.findById(id);
    }

    //update category
    public Admin updateAdmin(Admin category) {
        Admin existingAdmin = adminRepository.findById(category.getId()).orElse(null);
        existingAdmin.setName(category.getName());
        return adminRepository.save(existingAdmin);
    }

    //delete category
    public String deleteAdmin(Integer id) {
        adminRepository.deleteById(id);
        return "Admin removed !! " + id;
    }

    public long count(String name){
        Stream<Admin> data = adminRepository.findAll().stream();
        data = data.filter(product -> product.getName().toLowerCase().contains(name.toLowerCase()));

        return data.count();
    }



}
