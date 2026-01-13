package controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import entity.Role;
import entity.Users;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import services.UserService;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class UserControllerTest {

    private MockMvc mockMvc;

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void testCreateUser() throws Exception {
        Users inputUser = new Users("john_doe", "password123", Role.USER, "john@example.com");

        Users returnedUser = new Users("john_doe", "password123", Role.USER, "john@example.com");
        returnedUser.setId(1L);

        when(userService.createUser(any(Users.class))).thenReturn(returnedUser);

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputUser)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.username").value("john_doe"))
                .andExpect(jsonPath("$.role").value("USER"))
                .andExpect(jsonPath("$.email").value("john@example.com"));

        verify(userService, times(1)).createUser(any(Users.class));
    }


    @Test
    void testGetUserById() throws Exception {
        Users u1 = new Users("john_doe", "pass", Role.USER, "john@example.com");
        u1.setId(1L);

        when(userService.getUserById(1L)).thenReturn(u1);

        mockMvc.perform(get("/api/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.username").value("john_doe"))
                .andExpect(jsonPath("$.email").value("john@example.com"))
        .andExpect(jsonPath("$.role").value("USER"));

        verify(userService, times(1)).getUserById(1L);
    }

    @Test
    void testGetAllUsers() throws Exception {
        Users u1 = new Users("john_doe", "password123", Role.USER, "john@example.com");
        u1.setId(1L);

        Users u2 = new Users("jane_doe", "password123", Role.USER, "jane@example.com");
        u2.setId(2L);

        when(userService.getAllUsers()).thenReturn(List.of(u1, u2));

        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].username").value("john_doe"))
                .andExpect(jsonPath("$[0].email").value("john@example.com"))
                .andExpect(jsonPath("$[0].role").value("USER"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].username").value("jane_doe"))
                .andExpect(jsonPath("$[1].email").value("jane@example.com"))
        .andExpect(jsonPath("$[1].role").value("USER"));

        verify(userService, times(1)).getAllUsers();
    }


    @Test
    void testDeleteUser() throws Exception {
        doNothing().when(userService).deleteUser(1L);

        mockMvc.perform(delete("/api/users/1"))
                .andExpect(status().isOk());

        verify(userService, times(1)).deleteUser(1L);
    }
}
