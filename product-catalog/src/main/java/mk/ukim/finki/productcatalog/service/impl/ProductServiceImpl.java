package mk.ukim.finki.productcatalog.service.impl;

import lombok.RequiredArgsConstructor;
import mk.ukim.finki.productcatalog.domain.dtos.request.CreateProductDto;
import mk.ukim.finki.productcatalog.domain.dtos.request.FilterProductsDto;
import mk.ukim.finki.productcatalog.domain.dtos.response.GetProductDto;
import mk.ukim.finki.productcatalog.domain.exceptions.ProductNotFoundException;
import mk.ukim.finki.productcatalog.domain.mappers.ProductToGetCartItemDtoMapper;
import mk.ukim.finki.productcatalog.domain.mappers.ProductToGetProductDtoMapper;
import mk.ukim.finki.productcatalog.domain.models.Brand;
import mk.ukim.finki.productcatalog.domain.models.Category;
import mk.ukim.finki.productcatalog.domain.models.Product;
import mk.ukim.finki.productcatalog.domain.models.Size;
import mk.ukim.finki.productcatalog.repository.ProductRepository;
import mk.ukim.finki.productcatalog.service.*;
import mk.ukim.finki.sharedkernel.domain.dto.response.GetOrderItemDto;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final CategoryService categoryService;
    private final ProductToGetProductDtoMapper productMapper;
    private final ProductToGetCartItemDtoMapper productToGetCartItemDtoMapper;
    private final SizeService sizeService;
    private final BrandService brandService;
    private final ImageService imageService;

    @Override
    public Product insert(CreateProductDto dto) {
        Category category = this.categoryService.findById(dto.getCategoryId());
        Brand brand = this.brandService.findBrandById(dto.getBrandId());
        Product product = new Product(dto.getName(), dto.getDescription(), dto.getPrice(), dto.getCode(), brand, category);
        return this.productRepository.save(product);
    }

    @Override
    public Product findProductById(Long id) {
        return this.productRepository.findProductByIdAndIsDeletedFalse(id).orElseThrow(() -> new ProductNotFoundException(id.toString()));
    }

    @Override
    @Transactional
    public List<GetProductDto> findProductsByCategory(Long categoryId, Long userId) {
        List<Product> productsByCategory = this.productRepository.findAllByIsDeletedFalseAndCategoryId(categoryId);
        List<GetProductDto> dtoList = this.productMapper.toGetProductsList(productsByCategory);
        return dtoList.stream().map(p -> this.setIsInWishlist(p, userId)).collect(Collectors.toList());
    }

    @Override
    public List<GetProductDto> findFilteredProducts(FilterProductsDto dto, Long userId) {
        List<GetProductDto> dtoList = this.productMapper.toGetProductsList(this.productRepository.findAllByIsDeletedFalseAndCategoryIdAndPriceBetween(dto.getCategoryId(), dto.getMin(), dto.getMax()));
        return dtoList.stream().map(p -> this.setIsInWishlist(p, userId)).collect(Collectors.toList());
    }

    @Override
    public List<GetProductDto> findAllSearchedProducts(String searchText, Long userId) {
        List<GetProductDto> dtoList = this.productMapper.toGetProductsList(this.productRepository.findAllSearchedProducts(searchText.toLowerCase(Locale.ROOT)));
        return dtoList.stream().map(p -> this.setIsInWishlist(p, userId)).collect(Collectors.toList());
    }

    @Override
    public GetProductDto findProductByCode(String code, Long userId) {
        Product p = this.productRepository.findProductByCodeAndIsDeletedFalse(code).orElseThrow(() -> new ProductNotFoundException(code));
        return setIsInWishlist(this.productMapper.toGetProductDto(p), userId);
    }

    @Override
    public void uploadImagesForProduct(MultipartFile[] images, Long productId) throws IOException {
        Product product = this.findProductById(productId);
        for(MultipartFile imagePart: images) {
            this.imageService.uploadForImage(imagePart, product);
        }
    }

    @Override
    public GetProductDto setIsInWishlist(GetProductDto dto, Long userId) {
        if(userId<1) return dto;
        dto.setIsInWishlist(this.productRepository.isProductInWishlist(userId, dto.getId()));
        return dto;
    }

    @Override
    public Float findPriceForProduct(Long productId) {
        Product product = this.findProductById(productId);
        return product.getPrice();
    }

    @Override
    public GetOrderItemDto getCartItem(Long productId, Long sizeId) {
        Product product = this.findProductById(productId);
        Size size = this.sizeService.findById(sizeId);
        GetOrderItemDto dto = this.productToGetCartItemDtoMapper.toGetCartItem(product);
        dto.setSelectedSize(size.getSize());
        return dto;
    }
}
