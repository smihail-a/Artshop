package entity;
import jakarta.persistence.*;
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
@Table(name = "favorites", uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "artwork_id"}))
public class Favourites {

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

    public Favourites(Users user, Artwork artwork) {
        this.user = user;
        this.artwork = artwork;
    }

}

