package mk.ukim.finki.productcatalog.service.impl;

import lombok.RequiredArgsConstructor;
import mk.ukim.finki.productcatalog.domain.exceptions.ProductSizeNotFoundException;
import mk.ukim.finki.sharedkernel.domain.dto.request.ProductSizeDto;
import mk.ukim.finki.productcatalog.domain.models.Product;
import mk.ukim.finki.productcatalog.domain.models.ProductSize;
import mk.ukim.finki.productcatalog.domain.models.Size;
import mk.ukim.finki.productcatalog.repository.ProductSizeRepository;
import mk.ukim.finki.productcatalog.service.ProductService;
import mk.ukim.finki.productcatalog.service.ProductSizeService;
import mk.ukim.finki.productcatalog.service.SizeService;
import mk.ukim.finki.sharedkernel.kafka.KafkaGroups;
import mk.ukim.finki.sharedkernel.kafka.KafkaTopics;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class ProductSizeServiceImpl implements ProductSizeService {
    private final ProductSizeRepository productSizeRepository;
    private final ProductService productService;
    private final SizeService sizeService;

    @Override
    public Long addSizeForProduct(ProductSizeDto dto) {
        Product product = this.productService.findProductById(dto.getProductId());
        Size size = this.sizeService.findById(dto.getSizeId());
        return this.productSizeRepository.save(new ProductSize(product, size, dto.getQuantity())).getId();
    }

    @KafkaListener(topics = KafkaTopics.CHANGE_PRODUCT_SIZE_QUANTITY,
            groupId = KafkaGroups.CLOTHING_GROUP,
            containerFactory = "reduceProductSizeQuantityContainerFactory")
    @Transactional
    @Override
    public void reduceProductSizeQuantity(ProductSizeDto dto) {
        ProductSize productSize = this.productSizeRepository
                .findProductSizeByProductIdAndSizeIdAndIsDeletedFalse(dto.getProductId(), dto.getSizeId())
                .orElseThrow(() -> new ProductSizeNotFoundException(dto.getProductId(), dto.getSizeId()));
        productSize.setQuantity(productSize.getQuantity() + dto.getQuantity());
        this.productSizeRepository.save(productSize);
    }
}
