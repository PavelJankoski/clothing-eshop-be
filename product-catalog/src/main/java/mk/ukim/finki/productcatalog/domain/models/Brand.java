package mk.ukim.finki.productcatalog.domain.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "brand")
public class Brand {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "brand")
    private List<Product> products;

    private LocalDateTime createdOn;

    private LocalDateTime modifiedOn;

    @Column(name = "is_deleted")
    private Boolean isDeleted = false;

    public Brand(String name) {
        this.name = name;
        this.products = new ArrayList<>();
    }
}
