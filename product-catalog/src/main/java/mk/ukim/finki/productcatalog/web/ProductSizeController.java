package mk.ukim.finki.productcatalog.web;

import lombok.RequiredArgsConstructor;
import mk.ukim.finki.sharedkernel.domain.dto.request.ProductSizeDto;
import mk.ukim.finki.productcatalog.service.ProductSizeService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/products-sizes")
@CrossOrigin(value = "*")
@RequiredArgsConstructor
public class ProductSizeController {
    private final ProductSizeService productSizeService;

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Long> createProductSize(@RequestBody ProductSizeDto dto) {
        return ResponseEntity.ok(this.productSizeService.addSizeForProduct(dto));
    }
}
