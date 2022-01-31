package mk.ukim.finki.ordermanagement.service;

import com.stripe.exception.StripeException;

import java.util.Map;

public interface StripeService {
    Map<String, String> paymentSheetParams(Float amount) throws StripeException;
}
