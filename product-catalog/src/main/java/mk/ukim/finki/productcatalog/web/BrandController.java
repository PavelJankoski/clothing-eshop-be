package mk.ukim.finki.productcatalog.web;

import mk.ukim.finki.productcatalog.domain.dtos.request.CreateBrandDto;
import mk.ukim.finki.productcatalog.domain.dtos.request.EditBrandDto;
import mk.ukim.finki.productcatalog.domain.models.Brand;
import mk.ukim.finki.productcatalog.service.BrandService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/brands")
public class BrandController {
    private final BrandService brandService;

    public BrandController(BrandService brandService) {
        this.brandService = brandService;
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Brand> createBrand(@RequestBody CreateBrandDto dto) {
        return ResponseEntity.ok(this.brandService.insert(dto));
    }

    @PutMapping(value = "/{brandId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Brand> editBrand(@PathVariable Long brandId, @RequestBody EditBrandDto dto) {
        return ResponseEntity.ok(this.brandService.edit(brandId, dto));
    }
}
