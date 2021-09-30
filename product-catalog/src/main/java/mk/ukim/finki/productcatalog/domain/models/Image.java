package mk.ukim.finki.productcatalog.domain.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name = "image")
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String url;

    private LocalDateTime createdOn;

    private LocalDateTime modifiedOn;

    @ManyToOne
    @JsonIgnore
    private Product product;

    @Column(name = "is_deleted")
    private Boolean isDeleted = false;

    public Image(String url) {
        this.url = url;
    }
}
