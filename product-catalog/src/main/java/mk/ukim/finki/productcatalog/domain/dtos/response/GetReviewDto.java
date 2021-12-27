package mk.ukim.finki.productcatalog.domain.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class GetReviewDto implements Serializable {
    private Float rating;
    private String review;
    private String reviewedOn;
    private String fullName;
    private String imageUrl;
}
