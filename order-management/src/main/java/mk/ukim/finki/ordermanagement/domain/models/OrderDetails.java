package mk.ukim.finki.ordermanagement.domain.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mk.ukim.finki.ordermanagement.domain.enums.PaymentType;
import mk.ukim.finki.ordermanagement.domain.valueobjects.Address;
import mk.ukim.finki.sharedkernel.domain.model.base.BaseTimeAuditedEntity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "order_details")
public class OrderDetails extends BaseTimeAuditedEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private PaymentType paymentType;

    @Embedded
    private Address address;

    private Float totalAmount;

    @Column(name = "is_deleted")
    private Boolean isDeleted = false;

    public OrderDetails(PaymentType paymentType, Address address, Float totalAmount) {
        this.paymentType = paymentType;
        this.address = address;
        this.totalAmount = totalAmount;
    }
}
