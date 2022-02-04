package mk.ukim.finki.ordermanagement.domain.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mk.ukim.finki.ordermanagement.domain.valueobjects.Address;
import mk.ukim.finki.sharedkernel.domain.dto.response.GetAddressDto;
import mk.ukim.finki.sharedkernel.domain.dto.response.GetOrderProductDto;
import mk.ukim.finki.sharedkernel.domain.dto.response.UserInfoDto;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetOrderHistoryDetailsDto implements Serializable {
    private Long orderId;

    private String date;

    private Address addressDto;

    private List<GetOrderProductDto> orderProducts;

    private Float totalPrice;

}
