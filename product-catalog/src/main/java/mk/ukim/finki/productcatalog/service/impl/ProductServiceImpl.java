package mk.ukim.finki.productcatalog.service.impl;

import mk.ukim.finki.productcatalog.domain.dtos.request.CreateProductDto;
import mk.ukim.finki.productcatalog.domain.dtos.response.GetProductDto;
import mk.ukim.finki.productcatalog.domain.mappers.ProductToGetProductDtoMapper;
import mk.ukim.finki.productcatalog.domain.models.Brand;
import mk.ukim.finki.productcatalog.domain.models.Category;
import mk.ukim.finki.productcatalog.domain.models.Product;
import mk.ukim.finki.productcatalog.repository.ProductRepository;
import mk.ukim.finki.productcatalog.service.BrandService;
import mk.ukim.finki.productcatalog.service.CategoryService;
import mk.ukim.finki.productcatalog.service.ProductService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final CategoryService categoryService;
    private final ProductToGetProductDtoMapper productMapper;
    private final BrandService brandService;

    public ProductServiceImpl(ProductRepository productRepository, CategoryService categoryService, ProductToGetProductDtoMapper productMapper, BrandService brandService) {
        this.productRepository = productRepository;
        this.categoryService = categoryService;
        this.productMapper = productMapper;
        this.brandService = brandService;
    }

    @Override
    public Product insert(CreateProductDto dto) {
        Category category = this.categoryService.findById(dto.getCategoryId());
        Brand brand = this.brandService.findBrandById(dto.getBrandId());
        Product product = new Product(dto.getName(), dto.getDescription(), dto.getPrice(), dto.getCode(), brand, category);
        return this.productRepository.save(product);
    }

    @Override
    @Transactional
    public List<GetProductDto> findProductsByCategory(Long categoryId, Long userId) {
        List<Product> productsByCategory = this.productRepository.findAllByIsDeletedFalseAndCategoryId(categoryId);
        return this.productMapper.toGetProductsList(productsByCategory, userId);
    }
}
