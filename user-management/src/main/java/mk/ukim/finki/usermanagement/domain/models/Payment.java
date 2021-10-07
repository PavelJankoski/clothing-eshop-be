package mk.ukim.finki.usermanagement.domain.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "payment")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Integer number;

    private LocalDate expiresOn;

    private Integer cvvCode;

    @Column(nullable = false)
    private LocalDateTime createdOn;

    @Column(nullable = false)
    private LocalDateTime modifiedOn;

    @Column(name = "is_deleted")
    private Boolean isDeleted = false;

    @ManyToOne
    private Person person;

    public Payment(String name, Integer number, LocalDate expiresOn, Integer cvvCode) {
        this.name = name;
        this.number = number;
        this.expiresOn = expiresOn;
        this.cvvCode = cvvCode;
        this.createdOn = LocalDateTime.now();
        this.modifiedOn = LocalDateTime.now();
    }
}
