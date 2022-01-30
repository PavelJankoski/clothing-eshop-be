package mk.ukim.finki.productcatalog.service;

import mk.ukim.finki.sharedkernel.domain.dto.request.ProductSizeDto;

public interface ProductSizeService {
    Long addSizeForProduct(ProductSizeDto dto);

    void reduceProductSizeQuantity(ProductSizeDto dto);
}
