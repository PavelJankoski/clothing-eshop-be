package mk.ukim.finki.productcatalog.domain.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    private Float price;

    @Formula("(select coalesce(avg(r.rating), 0) from ratings r where r.product_id=id)")
    private Float starRating;

    @Formula("(select coalesce(count(r.rating), 0) from ratings r where r.product_id=id)")
    private Integer numRatings;

    @Column(name = "code", unique = true)
    private String code;

    @Column(nullable = false)
    private LocalDateTime createdOn;

    @Column(nullable = false)
    private LocalDateTime modifiedOn;

    @Column(name = "is_deleted")
    private Boolean isDeleted = false;

    @ManyToOne
    private Brand brand;

    @ManyToOne
    private Category category;

    @ManyToMany(targetEntity = Size.class)
    @JoinTable(
            name = "product_size",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "size_id"))
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
        this.createdOn = LocalDateTime.now();
        this.modifiedOn = LocalDateTime.now();
        this.sizes = new ArrayList<>();
        this.reviews = new ArrayList<>();
    }
}
