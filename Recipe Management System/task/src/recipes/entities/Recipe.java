package recipes.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "recipes")
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private long id;
    @NotBlank
    private String name;
    @NotBlank
    private String category;
    private LocalDateTime date = LocalDateTime.now();
    @NotBlank
    private String description;
    @NotEmpty
    @Size(min = 1)
    @ElementCollection
    private List<String> ingredients;
    @NotEmpty
    @Size(min = 1)
    @ElementCollection
    private List<String> directions;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users users;
}
