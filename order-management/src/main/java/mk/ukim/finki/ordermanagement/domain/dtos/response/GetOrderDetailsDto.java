package mk.ukim.finki.ordermanagement.domain.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mk.ukim.finki.sharedkernel.domain.dto.response.GetAddressDto;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetOrderDetailsDto implements Serializable {
    private GetAddressDto addressDto;

    private Float totalPrice;
}
