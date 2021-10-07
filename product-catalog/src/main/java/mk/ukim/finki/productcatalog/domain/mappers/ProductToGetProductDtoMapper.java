package mk.ukim.finki.productcatalog.domain.mappers;

import mk.ukim.finki.productcatalog.domain.dtos.response.GetProductDto;
import mk.ukim.finki.productcatalog.domain.dtos.response.GetSizeDto;
import mk.ukim.finki.productcatalog.domain.models.Image;
import mk.ukim.finki.productcatalog.domain.models.Product;
import mk.ukim.finki.productcatalog.repository.ProductRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProductToGetProductDtoMapper {
    private final ProductRepository productRepository;

    public ProductToGetProductDtoMapper(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    private GetProductDto toGetProductDto(Product product, Long userId) {
        GetProductDto dto = new GetProductDto();
        if(userId<1) {
            dto.setIsInWishlist(false);
            dto.setIsInShoppingCart(false);
        }
        else {
            dto.setIsInWishlist(this.productRepository.isProductInWishlist(userId, product.getId()));
        }
        List<GetSizeDto> sizes = product.getSizes().stream().map(s -> new GetSizeDto(s.getId(), s.getSize())).collect(Collectors.toList());
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

    public List<GetProductDto> toGetProductsList(List<Product> products, Long userId){
        return products.stream().map(p -> this.toGetProductDto(p, userId)).collect(Collectors.toList());
    }
}
