package mk.ukim.finki.usermanagement.domain.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mk.ukim.finki.usermanagement.domain.valueobjects.FullName;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "person")
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private FullName fullName;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    private String phoneNumber;

    @ManyToOne
    private Role role;

    @Column(nullable = false)
    private LocalDateTime createdOn;

    private LocalDateTime modifiedOn;

    @Column(name = "is_deleted")
    private Boolean isDeleted = false;

    @OneToMany(mappedBy = "person")
    private List<Payment> payments;

    @ManyToMany(targetEntity = Address.class)
    @JoinTable(
            name = "person_address",
            joinColumns = @JoinColumn(name = "person_id"),
            inverseJoinColumns = @JoinColumn(name = "address_id"))
    private List<Address> addresses;

    @OneToOne
    @JoinColumn(name = "details_id", referencedColumnName = "id")
    private Image image;

    public Person(FullName fullName, String email, String password, Role role, Image image) {
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.role = role;
        this.addresses = new ArrayList<>();
        this.payments = new ArrayList<>();
        this.image = image;
    }
}
