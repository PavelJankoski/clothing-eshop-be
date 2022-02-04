package mk.ukim.finki.ordermanagement.web;

import lombok.RequiredArgsConstructor;
import mk.ukim.finki.ordermanagement.domain.dtos.response.GetOrderHistoryDetailsDto;
import mk.ukim.finki.ordermanagement.domain.dtos.response.GetOrderHistoryDto;
import mk.ukim.finki.ordermanagement.domain.models.Order;
import mk.ukim.finki.ordermanagement.service.OrderService;
import mk.ukim.finki.sharedkernel.domain.dto.response.GetOrderItemDto;
import org.springframework.http.HttpStatus;
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

    @GetMapping(value = "/items-in-bag/{userId}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public ResponseEntity<Integer> getItemsInBagForUser(@PathVariable Long userId) {
        return ResponseEntity.ok(this.orderService.getItemsInBagNumber(userId));
    }

    @PostMapping(value = "/{userId}/place-order")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public HttpStatus placeOrder(@PathVariable Long userId) {
        this.orderService.placeOrder(userId);
        return HttpStatus.OK;
    }

    @GetMapping(value = "/{userId}/order-history")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public ResponseEntity<GetOrderHistoryDto> getOrderHistory(@PathVariable Long userId) {
        return ResponseEntity.ok(this.orderService.findDeliveredOrders(userId));
    }

    @GetMapping(value = "/{orderId}/order-history-details")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public ResponseEntity<GetOrderHistoryDetailsDto> getOrderHistoryDetails(@PathVariable Long orderId) {
        return ResponseEntity.ok(this.orderService.findDetailsForOrder(orderId));
    }
}
