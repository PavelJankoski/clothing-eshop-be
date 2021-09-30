package mk.ukim.finki.ordermanagement.domain.valueobjects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Embeddable
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Address {
    private String street;

    private String streetNo;

    private String city;

    private String country;

    private Integer postalCode;
}
