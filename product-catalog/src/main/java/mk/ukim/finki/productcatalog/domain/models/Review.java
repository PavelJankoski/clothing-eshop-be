package mk.ukim.finki.productcatalog.domain.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "review")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Float rating;

    private String review;

    private LocalDateTime reviewedOn;

    @Column(nullable = false)
    private LocalDateTime createdOn;

    private LocalDateTime modifiedOn;

    @Column(name = "is_deleted")
    private Boolean isDeleted = false;

    private Long userId;

    @ManyToOne
    @JsonIgnore
    private Product product;

    public Review(float rating, String review, Long userId, Product product) {
        this.rating = rating;
        this.review = review;
        this.userId = userId;
        this.product = product;
        this.reviewedOn = LocalDateTime.now();
    }
}
