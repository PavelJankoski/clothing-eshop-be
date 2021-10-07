package mk.ukim.finki.usermanagement.domain.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "address")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String street;

    private String streetNo;

    private String city;

    private String country;

    private Integer postalCode;

    @Column(nullable = false)
    private LocalDateTime createdOn;

    @Column(nullable = false)
    private LocalDateTime modifiedOn;

    @Column(name = "is_deleted")
    private Boolean isDeleted = false;

    public Address(String street, String streetNo, String city, String country, Integer postalCode) {
        this.street = street;
        this.streetNo = streetNo;
        this.city = city;
        this.country = country;
        this.postalCode = postalCode;
        this.createdOn = LocalDateTime.now();
        this.modifiedOn = LocalDateTime.now();
    }
}
