package mk.ukim.finki.ordermanagement.domain.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mk.ukim.finki.sharedkernel.domain.model.base.BaseTimeAuditedEntity;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "order_item")
public class OrderItem extends BaseTimeAuditedEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long productId;

    private Float price;

    private Long sizeId;

    private Integer quantity;

    @Column(name = "is_deleted")
    @ColumnDefault("false")
    private Boolean isDeleted = false;

    @ManyToOne
    @JsonIgnore
    private Order order;

    public OrderItem(Order order, Long productId, Float price, Long sizeId, Integer quantity) {
        this.order = order;
        this.productId = productId;
        this.price = price;
        this.sizeId = sizeId;
        this.quantity = quantity;
    }
}
