package mk.ukim.finki.usermanagement.domain.models;

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
@Table(name = "address")
public class Address extends BaseTimeAuditedEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String street;

    private String streetNo;

    private String city;

    private String country;

    private Integer postalCode;

    @Column(name = "is_deleted")
    @ColumnDefault("false")
    private Boolean isDeleted = false;

    @ManyToOne
    @JsonIgnore
    private User user;

    public Address(String street, String streetNo, String city, String country, Integer postalCode) {
        this.street = street;
        this.streetNo = streetNo;
        this.city = city;
        this.country = country;
        this.postalCode = postalCode;
    }
}
