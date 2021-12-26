package mk.ukim.finki.productcatalog.web;

import lombok.RequiredArgsConstructor;
import mk.ukim.finki.productcatalog.domain.dtos.response.GetProductDto;
import mk.ukim.finki.productcatalog.service.WishlistService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/wishlists")
@RequiredArgsConstructor
public class WishlistController {
    private final WishlistService wishlistService;

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @GetMapping(value = "/{userId}")
    ResponseEntity<List<GetProductDto>> getWishlistProductsForUser(@PathVariable Long userId) {
        return ResponseEntity.ok(this.wishlistService.wishlistProductsForUser(userId));
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @PatchMapping(value = "/add/{productId}/users/{userId}")
    ResponseEntity<Long> addProductToWishlistForUser(@PathVariable Long userId,
                                                     @PathVariable Long productId) {
        return ResponseEntity.ok(this.wishlistService.addProductToWishlistForUser(userId, productId));
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @PatchMapping(value = "/remove/{productId}/users/{userId}")
    ResponseEntity<Long> removeProductFromWishlistForUser(@PathVariable Long userId,
                                                          @PathVariable Long productId) {
        return ResponseEntity.ok(this.wishlistService.removeProductFromWishlistForUser(userId, productId));
    }
}
