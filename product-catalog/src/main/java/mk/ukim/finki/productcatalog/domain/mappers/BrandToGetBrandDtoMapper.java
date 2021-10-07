package mk.ukim.finki.productcatalog.domain.mappers;

import mk.ukim.finki.productcatalog.domain.dtos.response.GetBrandDto;
import mk.ukim.finki.productcatalog.domain.models.Brand;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class BrandToGetBrandDtoMapper {
    public GetBrandDto toGetBrandDto(Brand brand) {
        GetBrandDto dto = new GetBrandDto();
        dto.setId(brand.getId());
        dto.setName(brand.getName());

        return dto;
    }

    public List<GetBrandDto> toGetBrandDtoList(List<Brand> brands) {
        return brands.stream().map(this::toGetBrandDto).collect(Collectors.toList());
    }
}
