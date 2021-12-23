package mk.ukim.finki.usermanagement.service.impl;

import lombok.RequiredArgsConstructor;
import mk.ukim.finki.usermanagement.domain.dtos.request.CreateAddressDto;
import mk.ukim.finki.usermanagement.domain.dtos.request.EditAddressDto;
import mk.ukim.finki.usermanagement.domain.dtos.response.GetAddressDto;
import mk.ukim.finki.usermanagement.domain.exceptions.AddressNotFoundException;
import mk.ukim.finki.usermanagement.domain.mappers.AddressToGetAddressDtoMapper;
import mk.ukim.finki.usermanagement.domain.models.Address;
import mk.ukim.finki.usermanagement.domain.models.User;
import mk.ukim.finki.usermanagement.repository.AddressRepository;
import mk.ukim.finki.usermanagement.service.AddressService;
import mk.ukim.finki.usermanagement.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {
    private final AddressRepository addressRepository;
    private final UserService userService;
    private final AddressToGetAddressDtoMapper addressToGetAddressDtoMapper;

    @Override
    public List<Address> findAddressesForUser(Long userId) {
        return this.addressRepository.findAllByIsDeletedFalseAndUserId(userId);
    }

    @Override
    public Address findById(Long id) {
        return this.addressRepository.findAddressByIdAndIsDeletedFalse(id).orElseThrow(() -> new AddressNotFoundException(id));
    }

    @Override
    public GetAddressDto saveAddressForUser(Long userId, CreateAddressDto dto) {
        User user = this.userService.findUserById(userId);
        Address address = new Address(dto.getStreet(), dto.getStreetNo(), dto.getCity(), dto.getCountry(), dto.getPostalCode());
        address.setUser(user);
        Address addressResponse = this.addressRepository.save(address);
        this.checkIfDefault(dto.getIsDefault(), userId, addressResponse.getId());
        return this.addressToGetAddressDtoMapper.toGetAddressDto(addressResponse, dto.getIsDefault() ? addressResponse.getId() : 0L);
    }

    @Override
    public GetAddressDto editAddress(Long id, EditAddressDto dto) {
        Address address = this.findById(id);
        address.setCity(dto.getCity());
        address.setCountry(dto.getCountry());
        address.setStreet(dto.getStreet());
        address.setPostalCode(dto.getPostalCode());
        address.setStreetNo(dto.getStreetNo());
        return this.addressToGetAddressDtoMapper.toGetAddressDto(this.addressRepository.save(address), 0L);
    }

    @Override
    public List<GetAddressDto> getAddressesForUser(Long userId) {
        List<Address> addresses = this.findAddressesForUser(userId);
        Long defaultAddress = this.userService.getDefaultAddressId(userId);
        return this.addressToGetAddressDtoMapper.toGetAddressDtoList(addresses, defaultAddress);
    }

    @Override
    public Long setDefaultAddress(Long userId, Long addressId) {
        User user = this.userService.findUserById(userId);
        Address address = this.findById(addressId);
        user.setDefaultAddress(address);
        return this.userService.save(user).getDefaultAddress().getId();
    }

    private void checkIfDefault(Boolean isDefault, Long userId, Long addressId) {
        if(isDefault) {
            this.setDefaultAddress(userId, addressId);
        }
    }
}
