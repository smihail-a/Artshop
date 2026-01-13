package controller;
import entity.Users;
import services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@Tag(name = "Users", description = "User management")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Create a new user")
    @PostMapping
    public Users createUser(@Valid @RequestBody Users user) {
        return userService.createUser(user);
    }

    @Operation(summary = "Get user by ID")
    @GetMapping("/{id}")
    public Users getUser(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @Operation(summary = "Get all users")
    @GetMapping
    public List<Users> getAllUsers() {
        return userService.getAllUsers();
    }

    @Operation(summary = "Delete user")
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }
}