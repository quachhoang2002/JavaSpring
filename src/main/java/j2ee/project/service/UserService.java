package j2ee.project.service;

import j2ee.project.models.User;
import j2ee.project.repository.UserRepository;
import jakarta.persistence.TypedQuery;
import jakarta.validation.constraints.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @SuppressWarnings("unused")
    public User register(User user) {
        User existedUser = userRepository.findByEmail(user.getEmail());
        if (existedUser != null) {
            throw new RuntimeException("Email existed");
        }
        return userRepository.save(user);
    }
    public User updateUserByEmail(int id , User updatedUser) {
        User existingUser = userRepository.findById(id);
        if (existingUser != null) {
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
    public void remember(User user){
        userRepository.save(user);
    }

    public User findByToken(String token){
        return userRepository.findByToken(token);
    }

    public User findById(int id){ return userRepository.findById(id);}
    public User findByEmail(String email){ return userRepository.findByEmail(email);}

}
