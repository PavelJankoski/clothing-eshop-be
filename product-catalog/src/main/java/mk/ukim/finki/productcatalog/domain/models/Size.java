package mk.ukim.finki.productcatalog.domain.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mk.ukim.finki.sharedkernel.domain.model.base.BaseTimeAuditedEntity;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "size")
public class Size extends BaseTimeAuditedEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String size;

    private Integer quantity;

    @Column(name = "is_deleted")
    @ColumnDefault("false")
    private Boolean isDeleted = false;

    @ManyToMany(mappedBy = "sizes")
    @JsonIgnore
    private List<Product> products;

    public Size(String size, Integer quantity) {
        this.size = size;
        this.quantity = quantity;
        this.products = new ArrayList<>();
    }
}
