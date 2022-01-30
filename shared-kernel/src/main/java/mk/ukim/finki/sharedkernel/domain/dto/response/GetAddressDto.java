package mk.ukim.finki.sharedkernel.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetAddressDto implements Serializable {
    Long id;
    String street;
    String streetNo;
    String city;
    String country;
    Integer postalCode;
    Boolean isDefault;
}
