package services;
import entity.Order;
import org.springframework.stereotype.Service;
import repository.OrderRepository;

import java.util.List;

@Service
public class OrderService {


    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Order createOrder(Order order) {
        return orderRepository.save(order);
    }

    public List<Order> getOrdersByUser(Long buyerId) {
        return orderRepository.findByUserId(buyerId);
    }

}