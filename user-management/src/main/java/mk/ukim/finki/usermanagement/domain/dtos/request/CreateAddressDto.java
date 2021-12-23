package mk.ukim.finki.usermanagement.domain.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateAddressDto implements Serializable {
    String street;
    String streetNo;
    String city;
    String country;
    Integer postalCode;
    Boolean isDefault;
}
