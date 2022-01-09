package mk.ukim.finki.ordermanagement.web;

import lombok.RequiredArgsConstructor;
import mk.ukim.finki.ordermanagement.domain.dtos.request.AddProductToOrderDto;
import mk.ukim.finki.ordermanagement.domain.dtos.request.RemoveProductFromOrderDto;
import mk.ukim.finki.ordermanagement.domain.models.OrderItem;
import mk.ukim.finki.ordermanagement.service.OrderItemService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/order-items")
@CrossOrigin(value = "*")
@RequiredArgsConstructor
public class OrderItemController {
    private final OrderItemService orderItemService;

    @PostMapping(value = "/add-to-order")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public ResponseEntity<OrderItem> addProductToOrder(@RequestBody AddProductToOrderDto dto) {
        return ResponseEntity.ok(this.orderItemService.addProductToOrder(dto));
    }

    @PatchMapping(value = "/remove-from-order")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public HttpStatus removeProductFromOrder(@RequestBody RemoveProductFromOrderDto dto) {
        this.orderItemService.removeProductFromOrder(dto);
        return HttpStatus.OK;
    }
}
