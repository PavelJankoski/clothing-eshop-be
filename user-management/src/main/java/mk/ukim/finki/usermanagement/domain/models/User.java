package mk.ukim.finki.usermanagement.domain.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mk.ukim.finki.sharedkernel.domain.model.base.BaseTimeAuditedEntity;
import mk.ukim.finki.usermanagement.domain.valueobjects.FullName;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "person")
public class User extends BaseTimeAuditedEntity {
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

    @Column(name = "is_deleted")
    private Boolean isDeleted = false;

    @OneToMany(mappedBy = "user")
    private List<Address> addresses;

    @ManyToOne
    private Address defaultAddress;

    @OneToOne
    @JoinColumn(name = "image_id", referencedColumnName = "id")
    private Image image;

    public String getFormattedFullName() {
        return this.fullName.getFirstName() + " " + this.fullName.getLastName();
    }

    public User(FullName fullName, String email, String password, String phoneNumber, Role role, Image image) {
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.role = role;
        this.addresses = new ArrayList<>();
        this.image = image;
        this.phoneNumber=phoneNumber;
    }
}
