package mk.ukim.finki.productcatalog.service;

import mk.ukim.finki.productcatalog.domain.dtos.request.CreateProductDto;
import mk.ukim.finki.productcatalog.domain.dtos.request.FilterProductsDto;
import mk.ukim.finki.productcatalog.domain.dtos.response.GetProductDto;
import mk.ukim.finki.productcatalog.domain.models.Product;
import mk.ukim.finki.sharedkernel.domain.dto.response.GetOrderItemDto;
import mk.ukim.finki.sharedkernel.domain.dto.response.GetOrderProductDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ProductService {
    Product insert(CreateProductDto dto);

    Product findProductById(Long id);

    List<GetProductDto> findProductsByCategory(Long categoryId, Long userId);

    List<GetProductDto> findFilteredProducts(FilterProductsDto dto, Long userId);

    List<GetProductDto> findAllSearchedProducts(String searchText, Long userId);

    GetProductDto findProductByCode(String code, Long userId);

    GetProductDto setIsInWishlist(GetProductDto dto, Long userId);

    Float findPriceForProduct(Long productId);

    GetOrderItemDto getCartItem(Long productId, Long sizeId);

    void uploadImagesForProduct(MultipartFile[] images, Long productId) throws IOException;

    GetOrderProductDto getOrderProduct(Long productId, Long sizeId, Long userId);
}
