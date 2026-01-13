package entity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
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
@Table(name = "reviews")
public class Reviews {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @NotNull(message = "User is required")
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;

    @ManyToOne
    @NotNull(message = "Artwork is required")
    @JoinColumn(name = "artwork_id", nullable = false)
    private Artwork artwork;

    @Min(value = 1, message = "Rating must be at least 1")
    @Max(value = 5, message = "Rating cannot be more than 5")
    @Column(nullable = false)
    private int rating;

    @NotBlank(message = "Comment is required")
    @Column(columnDefinition = "TEXT")
    private String comment;

    public Reviews(Users user, Artwork artwork, int rating, String comment) {
        this.user= user;
        this.artwork = artwork;
        this.rating = rating;
        this.comment = comment;
    }
}
