package mk.ukim.finki.sharedkernel.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetOrderProductDto implements Serializable {
    private Long productId;

    private String name;

    private String size;

    private Float price;

    private String imageUrl;

    private Integer quantity;

    private Boolean isReviewed;
}
