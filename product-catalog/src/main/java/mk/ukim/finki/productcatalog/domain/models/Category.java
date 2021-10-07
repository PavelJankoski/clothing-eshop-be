package mk.ukim.finki.productcatalog.domain.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mk.ukim.finki.productcatalog.domain.enums.CategoryType;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private CategoryType type;

    private String name;


    private String imageUrl;

    @OneToMany(mappedBy = "category")
    private List<Product> products;

    @Column(nullable = false)
    private LocalDateTime createdOn;

    @Column(nullable = false)
    private LocalDateTime modifiedOn;

    @Column(name = "is_deleted")
    private Boolean isDeleted = false;

    public Category(CategoryType type, String name, String imageUrl) {
        this.type = type;
        this.name = name;
        this.imageUrl = imageUrl;
        this.createdOn = LocalDateTime.now();
        this.modifiedOn = LocalDateTime.now();
        this.products = new ArrayList<>();
    }
}
