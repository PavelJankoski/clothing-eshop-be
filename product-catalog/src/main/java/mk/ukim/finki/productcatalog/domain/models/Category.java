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

    @OneToOne
    @JoinColumn(name = "image_id", referencedColumnName = "id")
    private Image image;

    @OneToMany(mappedBy = "category")
    private List<Product> products;

    @Column(nullable = false)
    private LocalDateTime createdOn;

    private LocalDateTime modifiedOn;

    @Column(name = "is_deleted")
    private Boolean isDeleted = false;

    public Category(CategoryType type, String name, Image image) {
        this.type = type;
        this.name = name;
        this.image = image;
        this.products = new ArrayList<>();
    }
}
