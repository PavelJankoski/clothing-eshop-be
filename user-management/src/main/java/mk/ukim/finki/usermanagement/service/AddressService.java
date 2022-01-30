package mk.ukim.finki.usermanagement.service;

import mk.ukim.finki.usermanagement.domain.dtos.request.CreateEditAddressDto;
import mk.ukim.finki.sharedkernel.domain.dto.response.GetAddressDto;
import mk.ukim.finki.usermanagement.domain.models.Address;

import javax.validation.constraints.NotNull;
import java.util.List;

public interface AddressService {
    List<Address> findAddressesForUser(Long userId);

    Address findById(Long id);

    GetAddressDto saveAddressForUser(Long userId, CreateEditAddressDto dto);

    GetAddressDto editAddress(Long addressId, Long userId, CreateEditAddressDto dto);

    List<GetAddressDto> getAddressesForUser(Long userId);

    void setDefaultAddress(@NotNull Long userId, @NotNull Long addressId);

    void deleteAddress(@NotNull Long addressId);

    GetAddressDto getDefaultAddressForUser(@NotNull Long userId);
}
