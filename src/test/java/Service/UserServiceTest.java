package Service;
import entity.Role;
import entity.Users;
import repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.extension.ExtendWith;
import services.UserService;


@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void createUser_success() {
        Users user = new Users();
        user.setId(1L);
        user.setEmail("test@email.com");
        user.setPassword("test12345");
        user.setUsername("john14");
        user.setRole(Role.USER);

        when(userRepository.existsByEmail(user.getEmail())).thenReturn(false);
        when(userRepository.save(user)).thenReturn(user);

        Users saved = userService.createUser(user);

        assertNotNull(saved);
        verify(userRepository).save(user);
    }

    @Test
    void createUser_duplicateEmail_throwsException() {
        Users user = new Users();
        user.setId(1L);
        user.setEmail("test@email.com");
        user.setPassword("test12345");
        user.setUsername("john");
        user.setRole(Role.USER);

        when(userRepository.existsByEmail(user.getEmail())).thenReturn(true);

        assertThrows(IllegalArgumentException.class,
                () -> userService.createUser(user));
    }

    @Test
    void getUserById_notFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class,
                () -> userService.getUserById(1L));
    }
}