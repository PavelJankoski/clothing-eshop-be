package mk.ukim.finki.usermanagement.domain.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mk.ukim.finki.sharedkernel.domain.model.base.BaseTimeAuditedEntity;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "payment")
public class Payment extends BaseTimeAuditedEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Integer number;

    private LocalDate expiresOn;

    private Integer cvvCode;

    @Column(name = "is_deleted")
    private Boolean isDeleted = false;

    @ManyToOne
    @JsonIgnore
    private Person person;

    public Payment(String name, Integer number, LocalDate expiresOn, Integer cvvCode) {
        this.name = name;
        this.number = number;
        this.expiresOn = expiresOn;
        this.cvvCode = cvvCode;
    }
}
