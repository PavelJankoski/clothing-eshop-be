package mk.ukim.finki.usermanagement.domain.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetAddressDto implements Serializable {
    String street;
    String streetNo;
    String city;
    String country;
    Integer postalCode;
    Boolean isDefault;
}
