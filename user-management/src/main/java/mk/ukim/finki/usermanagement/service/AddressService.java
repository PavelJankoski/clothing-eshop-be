package mk.ukim.finki.usermanagement.service;

import mk.ukim.finki.usermanagement.domain.dtos.request.CreateAddressDto;
import mk.ukim.finki.usermanagement.domain.dtos.request.EditAddressDto;
import mk.ukim.finki.usermanagement.domain.dtos.response.GetAddressDto;
import mk.ukim.finki.usermanagement.domain.models.Address;

import javax.validation.constraints.NotNull;
import java.util.List;

public interface AddressService {
    List<Address> findAddressesForUser(Long userId);

    Address findById(Long id);

    GetAddressDto saveAddressForUser(Long userId, CreateAddressDto dto);

    GetAddressDto editAddress(Long id, EditAddressDto dto);

    List<GetAddressDto> getAddressesForUser(Long userId);

    Long setDefaultAddress(@NotNull Long userId, @NotNull Long addressId);

}
