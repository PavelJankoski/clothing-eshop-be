package mk.ukim.finki.usermanagement.domain.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mk.ukim.finki.usermanagement.domain.enums.RoleType;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "role")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private RoleType type;

    @OneToMany(mappedBy = "role")
    private List<Person> people;

    @Column(nullable = false)
    private LocalDateTime createdOn;

    private LocalDateTime modifiedOn;

    @Column(name = "is_deleted")
    private Boolean isDeleted = false;

    public Role(RoleType type) {
        this.type = type;
    }
}
