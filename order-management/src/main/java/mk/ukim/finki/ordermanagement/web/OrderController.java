package mk.ukim.finki.ordermanagement.web;

import lombok.RequiredArgsConstructor;
import mk.ukim.finki.ordermanagement.service.OrderService;
import mk.ukim.finki.sharedkernel.domain.dto.response.GetOrderItemDto;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/orders", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(value = "*")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @GetMapping(value = "/{userId}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public ResponseEntity<List<GetOrderItemDto>> getOrderItems(@PathVariable Long userId) {
        return ResponseEntity.ok(this.orderService.getOrderItems(userId));
    }

    @GetMapping(value = "/existsInShoppingCart/{userId}/{productId}")
    public ResponseEntity<Boolean> getIsProductInShoppingCart(@PathVariable Long userId, @PathVariable Long productId) {
        return ResponseEntity.ok(this.orderService.isProductInShoppingCart(userId, productId));
    }
}
