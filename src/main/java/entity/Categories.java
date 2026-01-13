package entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.validation.constraints.NotBlank;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table (name = "category")
public class Categories {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Category name is required")
    @Column(unique = true)
    private String name;

    public Categories(String name) { this.name = name; }

}