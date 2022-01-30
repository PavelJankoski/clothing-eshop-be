package mk.ukim.finki.ordermanagement.domain.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChangeOrderItemQuantityDto implements Serializable {
    private Long userId;
    private Long productId;
    private Long sizeId;
    private Integer quantity;
}