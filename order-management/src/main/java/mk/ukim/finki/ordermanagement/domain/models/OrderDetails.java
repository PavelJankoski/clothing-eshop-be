package mk.ukim.finki.ordermanagement.domain.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mk.ukim.finki.ordermanagement.domain.valueobjects.Address;
import mk.ukim.finki.sharedkernel.domain.model.base.BaseTimeAuditedEntity;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "order_details")
public class OrderDetails extends BaseTimeAuditedEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private Address address;

    private Float totalAmount;

    @Column(name = "is_deleted")
    @ColumnDefault("false")
    private Boolean isDeleted = false;

    public OrderDetails(Address address) {
        this.address = address;
    }
}
