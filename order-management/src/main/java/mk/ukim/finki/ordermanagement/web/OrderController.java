package mk.ukim.finki.ordermanagement.web;

import mk.ukim.finki.ordermanagement.service.OrderService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/orders")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping(value = "/existsInShoppingCart/{userId}/{productId}", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> getIsProductInShoppingCart(@PathVariable Long userId, @PathVariable Long productId) {
        return ResponseEntity.ok(this.orderService.isProductInShoppingCart(userId, productId));
    }
}
