package mk.ukim.finki.productcatalog.domain.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "size")
public class Size {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String size;

    private LocalDateTime createdOn;

    private LocalDateTime modifiedOn;

    @Column(name = "is_deleted")
    private Boolean isDeleted = false;

    public Size(String size) {
        this.size = size;
    }
}
