package mk.ukim.finki.productcatalog.domain.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mk.ukim.finki.sharedkernel.domain.model.base.BaseTimeAuditedEntity;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Formula;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "product")
public class Product extends BaseTimeAuditedEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    private Float price;

    @Formula("(select coalesce(avg(r.rating), 0) from review r where r.product_id=id)")
    private Float starRating;

    @Formula("(select coalesce(count(r.rating), 0) from review r where r.product_id=id)")
    private Integer numRatings;

    @Column(name = "code", unique = true)
    private String code;

    @Column(name = "is_deleted")
    @ColumnDefault("false")
    private Boolean isDeleted = false;

    @ManyToOne
    private Brand brand;

    @ManyToOne
    private Category category;

    @ManyToMany()
    private List<Size> sizes;

    @OneToMany(mappedBy = "product")
    private List<Review> reviews;

    @OneToMany(mappedBy = "product")
    private List<Image> images;

    public Product(String name, String description, Float price, String code, Brand brand, Category category) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.code = code;
        this.brand = brand;
        this.category = category;
        this.sizes = new ArrayList<>();
        this.reviews = new ArrayList<>();
    }
}
