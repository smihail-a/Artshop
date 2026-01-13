package controller;
import entity.Order;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import services.OrderService;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@Tag(name = "Categories", description = "Artwork purchases")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @Operation(summary = "Create order")
    @PostMapping
    public Order createOrder(@Valid @RequestBody Order order) {
        return orderService.createOrder(order);
    }

    @Operation(summary = "Get orders by buyer")
    @GetMapping("/user/{userId}")
    public List<Order> byBuyer(@PathVariable Long userId) {
        return orderService.getOrdersByUser(userId);
    }

}
