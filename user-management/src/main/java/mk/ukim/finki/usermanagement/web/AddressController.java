package mk.ukim.finki.usermanagement.web;

import lombok.RequiredArgsConstructor;
import mk.ukim.finki.usermanagement.domain.dtos.request.CreateAddressDto;
import mk.ukim.finki.usermanagement.domain.dtos.request.EditAddressDto;
import mk.ukim.finki.usermanagement.domain.dtos.response.GetAddressDto;
import mk.ukim.finki.usermanagement.service.AddressService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/addresses")
@CrossOrigin(value = "*")
@RequiredArgsConstructor
public class AddressController {
    private final AddressService addressService;

    @GetMapping(value = "/{userId}")
    public ResponseEntity<List<GetAddressDto>> getAddressesForUser(@PathVariable Long userId) {
        return ResponseEntity.ok(this.addressService.getAddressesForUser(userId));
    }

    @PostMapping("/{userId}")
    public ResponseEntity<GetAddressDto> createAddressForUser(@PathVariable Long userId,
                                                              @RequestBody CreateAddressDto dto) {
        return ResponseEntity.ok(this.addressService.saveAddressForUser(userId, dto));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<GetAddressDto> editAddress(@PathVariable Long id,
                                                     @RequestBody EditAddressDto dto) {
        return ResponseEntity.ok(this.addressService.editAddress(id, dto));
    }

    @PatchMapping("/{addressId}/users/{userId}")
    public ResponseEntity<Long> setDefaultAddressForUser(@PathVariable Long userId,
                                                         @PathVariable Long addressId) {
        return ResponseEntity.ok(this.addressService.setDefaultAddress(userId, addressId));
    }
}
