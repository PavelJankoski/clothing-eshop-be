package mk.ukim.finki.productcatalog.service.impl;

import lombok.RequiredArgsConstructor;
import mk.ukim.finki.productcatalog.domain.dtos.response.GetProductDto;
import mk.ukim.finki.productcatalog.domain.mappers.ProductToGetProductDtoMapper;
import mk.ukim.finki.productcatalog.domain.models.Product;
import mk.ukim.finki.productcatalog.domain.models.Wishlist;
import mk.ukim.finki.productcatalog.repository.WishlistRepository;
import mk.ukim.finki.productcatalog.service.ProductService;
import mk.ukim.finki.productcatalog.service.WishlistService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WishlistServiceImpl implements WishlistService {
    private final WishlistRepository wishlistRepository;
    private final ProductService productService;
    private final ProductToGetProductDtoMapper productToGetProductDtoMapper;

    @Override
    public List<GetProductDto> wishlistProductsForUser(Long userId) {
        Wishlist wishlist = getOrCreateWishlistForUser(userId);
        return this.productToGetProductDtoMapper.toGetProductsList(wishlist.getProducts())
                .stream()
                .map(dto -> this.productService.setIsInWishlist(dto, userId))
                .collect(Collectors.toList());
    }

    @Override
    public Long addProductToWishlistForUser(Long userId, Long productId) {
        Wishlist wishlist = getOrCreateWishlistForUser(userId);
        Product product = this.productService.findProductById(productId);
        List<Product> wishlistProducts = wishlist.getProducts();
        wishlistProducts.add(product);
        wishlist.setProducts(wishlistProducts);
        this.wishlistRepository.save(wishlist);
        return productId;
    }

    @Override
    public Long removeProductFromWishlistForUser(Long userId, Long productId) {
        Wishlist wishlist = getOrCreateWishlistForUser(userId);
        wishlist.setProducts(wishlist
                .getProducts()
                .stream()
                .filter(p -> !p.getId().equals(productId))
                .collect(Collectors.toList()));
        this.wishlistRepository.save(wishlist);
        return productId;
    }

    private Wishlist getOrCreateWishlistForUser(Long userId) {
        Wishlist wishlist = this.wishlistRepository.findWishlistByUserIdAndIsDeletedFalse(userId).orElse(null);
        if(wishlist == null) return this.wishlistRepository.save(new Wishlist(userId));
        return wishlist;
    }
}
