package mk.ukim.finki.ordermanagement.domain.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mk.ukim.finki.ordermanagement.domain.enums.OrderStatus;
import mk.ukim.finki.sharedkernel.domain.model.base.BaseTimeAuditedEntity;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "orders")
public class Order extends BaseTimeAuditedEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "order")
    private List<OrderItem> orderItems;

    @Column(name = "is_deleted")
    @ColumnDefault("false")
    private Boolean isDeleted = false;

    @OneToOne
    @JoinColumn(name = "details_id", referencedColumnName = "id")
    private OrderDetails details;

    public Order(Long userId) {
        this.userId = userId;
        this.status = OrderStatus.PENDING;
        this.orderItems = new ArrayList<>();
    }
}
