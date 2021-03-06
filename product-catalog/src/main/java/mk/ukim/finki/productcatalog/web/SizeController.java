package mk.ukim.finki.productcatalog.web;

import lombok.RequiredArgsConstructor;
import mk.ukim.finki.productcatalog.domain.dtos.request.CreateSizeDto;
import mk.ukim.finki.productcatalog.domain.models.Size;
import mk.ukim.finki.productcatalog.service.SizeService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/sizes")
@RequiredArgsConstructor
public class SizeController {
    private final SizeService sizeService;

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Size> createSize(@RequestBody CreateSizeDto dto) {
        return ResponseEntity.ok(this.sizeService.insert(dto));
    }
}
