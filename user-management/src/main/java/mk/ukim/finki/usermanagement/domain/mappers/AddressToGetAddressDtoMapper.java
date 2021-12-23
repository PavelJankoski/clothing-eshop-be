package mk.ukim.finki.usermanagement.domain.mappers;

import mk.ukim.finki.usermanagement.domain.dtos.response.GetAddressDto;
import mk.ukim.finki.usermanagement.domain.models.Address;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AddressToGetAddressDtoMapper {
    public GetAddressDto toGetAddressDto(Address address, Long defaultAddress) {
        GetAddressDto dto = new GetAddressDto();
        dto.setCity(address.getCity());
        dto.setCountry(address.getCountry());
        dto.setStreet(address.getStreet());
        dto.setPostalCode(address.getPostalCode());
        dto.setStreetNo(address.getStreetNo());
        dto.setIsDefault(address.getId().equals(defaultAddress));
        return dto;
    }

    public List<GetAddressDto> toGetAddressDtoList(List<Address> addresses, Long defaultAddress) {
        return addresses.stream().map(a -> this.toGetAddressDto(a, defaultAddress)).collect(Collectors.toList());
    }
}
