package mk.ukim.finki.productcatalog.service;

import mk.ukim.finki.productcatalog.domain.dtos.response.GetProductDto;
import mk.ukim.finki.productcatalog.domain.models.Product;
import mk.ukim.finki.productcatalog.domain.models.Wishlist;

import java.util.List;

public interface WishlistService {
    List<GetProductDto> wishlistProductsForUser(Long userId);

    Long addProductToWishlistForUser(Long userId, Long productId);

    Long removeProductFromWishlistForUser(Long userId, Long productId);

}
