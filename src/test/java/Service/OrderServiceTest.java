package Service;
import entity.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import repository.OrderRepository;
import services.OrderService;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderService orderService;

    private Order order1;
    private Order order2;
    private Artwork artwork1;
    private Artwork artwork2;
    private Users artist;
    private Categories category;
    private Users user;
    private Users user2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        Users user = new Users("john_doe", "password", Role.USER, "john@example.com");
        user.setId(1L);

        Categories category = new Categories();
        category.setId(1L);
        category.setName("Painting");

       artwork1 = new Artwork(
                "Starry Night",
                "Beautiful painting",
                new BigDecimal("500.00"),  // price must be BigDecimal
                "image_url1",
                user,
                category
        );
        artwork1.setId(100L);
        artwork2 = new Artwork(
                "Mona Lisa",
                "Beautiful painting",
                new BigDecimal("600.00"),  // price must be BigDecimal
                "image_url2",
                user,
                category
        );
        artwork2.setId(200L);
        order1 = new Order(
                user,
                LocalDateTime.now(),
                artwork1,
                new BigDecimal("500.00")   // totalAmount must be BigDecimal
        );
        order1.setId(1L);

        order2 = new Order(
                user,
                LocalDateTime.now(),
                artwork2,
                new BigDecimal("600.00")
        );
        order2.setId(2L);
    }

    @Test
    void testCreateOrder() {
        when(orderRepository.save(order1)).thenReturn(order1);

        Order savedOrder = orderService.createOrder(order1);

        assertNotNull(savedOrder);
        assertEquals(new BigDecimal("500.00"), savedOrder.getTotalAmount());
        verify(orderRepository, times(1)).save(order1);
    }


    @Test
    void testGetOrdersByUser() {
        when(orderRepository.findByUserId(order1.getUser().getId()))
                .thenReturn(Arrays.asList(order1, order2));

        List<Order> orders = orderService.getOrdersByUser(order1.getUser().getId());

        assertEquals(2, orders.size());
        assertEquals(new BigDecimal("500.00"), orders.get(0).getTotalAmount());
        assertEquals(new BigDecimal("600.00"), orders.get(1).getTotalAmount());
    }

}
