package mk.ukim.finki.sharedkernel.domain.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductSizeDto implements Serializable {
    private Long productId;

    private Long  sizeId;

    private Integer quantity;
}
