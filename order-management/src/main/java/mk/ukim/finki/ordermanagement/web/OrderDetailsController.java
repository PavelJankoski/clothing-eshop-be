package mk.ukim.finki.ordermanagement.web;

import lombok.RequiredArgsConstructor;
import mk.ukim.finki.ordermanagement.domain.dtos.response.GetOrderDetailsDto;
import mk.ukim.finki.ordermanagement.service.OrderDetailsService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/order-details", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(value = "*")
@RequiredArgsConstructor
public class OrderDetailsController {
    private final OrderDetailsService orderDetailsService;

    @GetMapping("/{userId}")
    public ResponseEntity<GetOrderDetailsDto> getOrderDetailsForUser(@PathVariable Long userId) {
        return ResponseEntity.ok(this.orderDetailsService.findOrderDetailsForUser(userId));
    }
}
