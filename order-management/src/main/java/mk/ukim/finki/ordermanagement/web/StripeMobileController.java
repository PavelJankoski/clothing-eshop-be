package mk.ukim.finki.ordermanagement.web;

import com.stripe.exception.StripeException;
import lombok.RequiredArgsConstructor;
import mk.ukim.finki.ordermanagement.service.StripeService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(value = "/stripe")
@CrossOrigin(value = "*")
@RequiredArgsConstructor
public class StripeMobileController {
    private final StripeService stripeService;

    @GetMapping("/payment-sheet")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public ResponseEntity<Map<String, String>> getPaymentSheetParams(@RequestParam Float amount) throws StripeException {
        return ResponseEntity.ok(this.stripeService.paymentSheetParams(amount));
    }
}
