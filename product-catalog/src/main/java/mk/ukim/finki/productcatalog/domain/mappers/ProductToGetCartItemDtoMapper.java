package mk.ukim.finki.productcatalog.domain.mappers;

import mk.ukim.finki.productcatalog.domain.models.Image;
import mk.ukim.finki.productcatalog.domain.models.Product;
import mk.ukim.finki.sharedkernel.domain.dto.response.GetOrderItemDto;
import mk.ukim.finki.sharedkernel.domain.dto.response.GetSizeDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProductToGetCartItemDtoMapper {
    public GetOrderItemDto toGetCartItem(Product product) {
        GetOrderItemDto dto = new GetOrderItemDto();
        List<GetSizeDto> sizes = product.getProductSizes()
                .stream()
                .map(s -> new GetSizeDto(s.getSize().getId(), s.getSize().getSize(), s.getQuantity())).collect(Collectors.toList());
        List<String> images = product.getImages().stream().map(Image::getUrl).collect(Collectors.toList());
        dto.setName(product.getName());
        dto.setSizes(sizes);
        dto.setImageUrl(images.get(0));
        return dto;
    }
}
