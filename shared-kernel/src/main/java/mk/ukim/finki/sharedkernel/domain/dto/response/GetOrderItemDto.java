package mk.ukim.finki.sharedkernel.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetOrderItemDto implements Serializable {
    private Long id;
    private Float price;
    private String name;
    private List<GetSizeDto> sizes;
    private String selectedSize;
    private Integer selectedQuantity;
    private String imageUrl;
    private Long productId;
}
