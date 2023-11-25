package j2ee.project.service;

import j2ee.project.models.Admin;
import j2ee.project.models.User;
import j2ee.project.repository.UserRepository;
import jakarta.persistence.TypedQuery;
import jakarta.validation.constraints.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAll(int page, long limit, String name, Integer CustomerStatus) {
        //get and sort by id
        Stream<User> data = userRepository.findAll().stream();
        data = data.filter(product -> product.getName().toLowerCase().contains(name.toLowerCase()));
        //sort by role
        if (CustomerStatus != null) {
            data = data.filter(product -> product.getStatus() == CustomerStatus);
        }

        return data.skip((page - 1) * limit).limit(limit).toList();
    }

    public long count(String name, Integer CustomerStatus) {
        Stream<User> data = userRepository.findAll().stream();
        data = data.filter(product -> product.getName().toLowerCase().contains(name.toLowerCase()));

        if (CustomerStatus != null) {
            data = data.filter(product -> product.getStatus() == CustomerStatus);
        }

        return data.count();
    }

    @SuppressWarnings("unused")
    public User register(User user) {
        User existedUser = userRepository.findByEmail(user.getEmail());
        if (existedUser != null) {
            throw new RuntimeException("Email existed");
        }
        return userRepository.save(user);
    }

    public User updateUserByEmail(int id, User updatedUser) {
        User existingUser = userRepository.findById(id);

        if (existingUser != null) {
            User user = userRepository.findByEmail(updatedUser.getEmail());
            if (user != null && user.getId() != id) {
                throw new RuntimeException("Email existed");
            }


            System.out.println("id: " + id);
            System.out.println("existingUser.getId(): " + existingUser.getId());
            System.out.println("existingUser.getEmail(): " + existingUser.getEmail());
            System.out.println("updatedUser.getEmail(): " + updatedUser.getEmail());


            existingUser.setName(updatedUser.getName());
            existingUser.setEmail(updatedUser.getEmail());
            existingUser.setPhone(updatedUser.getPhone());
            existingUser.setPassword(updatedUser.getPassword());
            return userRepository.save(existingUser);
        } else {
            // Xử lý trường hợp không tìm thấy người dùng theo email
            throw new RuntimeException("Không tìm thấy người dùng với id: " + id);
        }
    }

    @SuppressWarnings("unused")
    public User login(String email, String password) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new RuntimeException("Email not found");
        }

        if (user == null) {
            throw new RuntimeException("Email not found");
        }
        if (!user.getPassword().equals(password)) {
            throw new RuntimeException("Password is incorrect");
        }

        return user;
    }

    public void update(User user) {
        user.setToken(null);
        userRepository.save(user);
    }

    //remember
    public void remember(User user) {
        userRepository.save(user);
    }

    public User findByToken(String token) {
        return userRepository.findByToken(token);
    }

    public User findById(int id) {
        return userRepository.findById(id);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

}
