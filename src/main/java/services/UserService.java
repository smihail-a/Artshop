package services;

import entity.Users;
import org.springframework.stereotype.Service;
import repository.UserRepository;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Users createUser(Users user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new IllegalArgumentException("Email already in use");
        }
        if(userRepository.existsByUsername(user.getUsername())) {
            throw new IllegalArgumentException("Username already in use");
        }
        return userRepository.save(user);
    }

    public Users getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
    }

    public List<Users> getAllUsers() {
        return userRepository.findAll();
    }
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new IllegalArgumentException("User not found");
        }
        userRepository.deleteById(id);
    }
}