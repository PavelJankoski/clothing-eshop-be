package mk.ukim.finki.productcatalog.domain.mappers;

import mk.ukim.finki.productcatalog.domain.dtos.response.GetBrandDto;
import mk.ukim.finki.productcatalog.domain.dtos.response.GetCategoryDto;
import mk.ukim.finki.productcatalog.domain.models.Brand;
import mk.ukim.finki.productcatalog.domain.models.Category;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CategoryToGetCategoryDtoMapper {
    public GetCategoryDto toGetCategoryDto(Category category) {
        GetCategoryDto dto = new GetCategoryDto();
        dto.setId(category.getId());
        dto.setName(category.getName());
        dto.setImageUrl(category.getImageUrl());
        dto.setType(category.getType().name());

        return dto;
    }

    public List<GetCategoryDto> toGetCategoryDtoList(List<Category> categories) {
        return categories.stream().map(this::toGetCategoryDto).collect(Collectors.toList());
    }
}
