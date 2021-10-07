package mk.ukim.finki.productcatalog.service;

import mk.ukim.finki.productcatalog.domain.dtos.request.CreateProductDto;
import mk.ukim.finki.productcatalog.domain.dtos.response.GetProductDto;
import mk.ukim.finki.productcatalog.domain.models.Product;

import java.util.List;

public interface ProductService {
    Product insert(CreateProductDto dto);

    List<GetProductDto> findProductsByCategory(Long categoryId, Long userId);
}
