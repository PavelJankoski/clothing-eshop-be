package mk.ukim.finki.productcatalog.service;

import mk.ukim.finki.productcatalog.domain.dtos.request.CreateProductDto;
import mk.ukim.finki.productcatalog.domain.dtos.response.GetProductDto;
import mk.ukim.finki.productcatalog.domain.models.Product;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ProductService {
    Product insert(CreateProductDto dto);

    Product findProductById(Long id);

    List<GetProductDto> findProductsByCategory(Long categoryId, Long userId, String token);

    void uploadImagesForProduct(MultipartFile[] images, Long productId) throws IOException;
}
