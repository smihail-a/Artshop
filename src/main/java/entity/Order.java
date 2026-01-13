package entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @NotNull(message = "Buyer is required")
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;

    @ManyToOne
    @NotNull(message = "Artwork is required")
    @JoinColumn(name = "artwork_id", nullable = false)
    private Artwork artwork;

    @Column(nullable = false)
    @NotNull(message = "Order date is required")
    private LocalDateTime orderDate;

    @Column(nullable = false)
    @NotNull(message = "Total amount payed is required")
    private BigDecimal totalAmount;

    public Order(Users user, LocalDateTime orderDate, Artwork artwork, BigDecimal totalAmount) {
        this.user = user;
        this.orderDate = orderDate;
        this.totalAmount = totalAmount;
        this.artwork = artwork;
    }
}
