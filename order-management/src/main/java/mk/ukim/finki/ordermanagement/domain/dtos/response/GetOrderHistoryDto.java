package mk.ukim.finki.ordermanagement.domain.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetOrderHistoryDto implements Serializable {
    private Integer totalOrders;

    private List<GetOrderHistoryItemDto> orderHistoryItemDtos;
}
