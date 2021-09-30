package mk.ukim.finki.productcatalog.service.impl;

import mk.ukim.finki.productcatalog.domain.dtos.request.CreateProductDto;
import mk.ukim.finki.productcatalog.domain.models.Category;
import mk.ukim.finki.productcatalog.domain.models.Product;
import mk.ukim.finki.productcatalog.repository.ProductRepository;
import mk.ukim.finki.productcatalog.service.CategoryService;
import mk.ukim.finki.productcatalog.service.ProductService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final CategoryService categoryService;

    public ProductServiceImpl(ProductRepository productRepository, CategoryService categoryService) {
        this.productRepository = productRepository;
        this.categoryService = categoryService;
    }

    @Override
    public Product insert(CreateProductDto dto) {
        Category category = this.categoryService.findById(dto.getCategoryId());
        Product product = new Product(dto.getName(), dto.getDescription(), dto.getPrice(), dto.getCode(), dto.getBrand(), category);
        product.setCreatedOn(LocalDateTime.now());
        return this.productRepository.save(product);
    }
}
