package controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import entity.Artwork;
import entity.Order;
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
import services.OrderService;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class OrderControllerTest {

    private MockMvc mockMvc;

    @Mock
    private OrderService orderService;

    @InjectMocks
    private OrderController orderController;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(orderController).build();
        objectMapper = new ObjectMapper();

        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.findAndRegisterModules();
    }

    @Test
    void testCreateOrder() throws Exception {
        Users user = new Users("john_doe", "password123", Role.USER, "john@example.com");
        user.setId(1L);
        Artwork artwork = new Artwork(
                "Starry Night",
                "Famous painting",
                new BigDecimal("1000"),
                "http://example.com/image.jpg",
                user, // artist can be the same user
                null // category not required for favourite
        );
        artwork.setId(100L);

        Order order = new Order(user, LocalDateTime.now(),artwork, new BigDecimal("1500.00"));
        order.setId(1L);

        when(orderService.createOrder(any(Order.class))).thenReturn(order);

        mockMvc.perform(post("/api/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(order)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.user.id").value(1))
                .andExpect(jsonPath("$.totalAmount").value(1500.00))
        .andExpect(jsonPath("$.artwork.id").value(100));

        verify(orderService, times(1)).createOrder(any(Order.class));
    }

    @Test
    void testGetOrdersByUser() throws Exception {
        Users user = new Users("john_doe", "password123", Role.USER, "john@example.com");
        user.setId(1L);
        Artwork artwork = new Artwork(
                "Starry Night",
                "Famous painting",
                new BigDecimal("1000"),
                "http://example.com/image.jpg",
                user, // artist can be the same user
                null // category not required for favourite
        );
        artwork.setId(100L);

        Order order = new Order(user, LocalDateTime.now(), artwork, new BigDecimal("1500.00"));
        order.setId(1L);

        when(orderService.getOrdersByUser(1L)).thenReturn(List.of(order));

        mockMvc.perform(get("/api/orders/user/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].user.id").value(1))
                .andExpect(jsonPath("$[0].totalAmount").value(1500.00))
        .andExpect(jsonPath("$[0].artwork.id").value(100));

        verify(orderService, times(1)).getOrdersByUser(1L);
    }
}
