package mk.ukim.finki.productcatalog.domain.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateReviewDto implements Serializable {
    private Float rating;
    private String review;
    private Long userId;
    private Long productId;
}
