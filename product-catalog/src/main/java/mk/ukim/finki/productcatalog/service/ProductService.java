package mk.ukim.finki.productcatalog.service;

import mk.ukim.finki.productcatalog.domain.dtos.request.CreateProductDto;
import mk.ukim.finki.productcatalog.domain.models.Product;

public interface ProductService {
    Product insert(CreateProductDto dto);
}
