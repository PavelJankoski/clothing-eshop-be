package mk.ukim.finki.productcatalog.domain.mappers;

import mk.ukim.finki.productcatalog.domain.dtos.response.GetProductDto;
import mk.ukim.finki.sharedkernel.domain.dto.response.GetSizeDto;
import mk.ukim.finki.productcatalog.domain.models.Image;
import mk.ukim.finki.productcatalog.domain.models.Product;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProductToGetProductDtoMapper {
    public GetProductDto toGetProductDto(Product product) {
        GetProductDto dto = new GetProductDto();
        List<GetSizeDto> sizes = product.getProductSizes()
                .stream()
                .filter(s -> !s.getIsDeleted() && s.getQuantity() > 0)
                .map(s -> new GetSizeDto(s.getId(), s.getSize().getSize(), s.getQuantity())).collect(Collectors.toList());
        List<String> images = product.getImages().stream().map(Image::getUrl).collect(Collectors.toList());
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setDescription(product.getDescription());
        dto.setPrice(product.getPrice());
        dto.setStarRating(product.getStarRating());
        dto.setNumRatings(product.getNumRatings());
        dto.setBrand(product.getBrand().getName());
        dto.setCode(product.getCode());
        dto.setSizes(sizes);
        dto.setImages(images);
        return dto;
    }


    public List<GetProductDto> toGetProductsList(List<Product> products) {
        return products.stream().map(this::toGetProductDto).collect(Collectors.toList());
    }
}
