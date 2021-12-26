package mk.ukim.finki.productcatalog.domain.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mk.ukim.finki.sharedkernel.domain.model.base.BaseTimeAuditedEntity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "wishlist")
public class Wishlist extends BaseTimeAuditedEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    @ManyToMany(targetEntity = Product.class)
    @JoinTable(
            name = "wishlist_products",
            joinColumns = @JoinColumn(name = "wishlist_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id"))
    private List<Product> products;

    @Column(name = "is_deleted")
    private Boolean isDeleted = false;

    public Wishlist(Long userId) {
        this.userId = userId;
        this.products = new ArrayList<>();
    }
}
