package mk.ukim.finki.ordermanagement.service.impl;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.model.EphemeralKey;
import com.stripe.model.PaymentIntent;
import com.stripe.net.RequestOptions;
import com.stripe.param.CustomerCreateParams;
import com.stripe.param.EphemeralKeyCreateParams;
import com.stripe.param.PaymentIntentCreateParams;
import mk.ukim.finki.ordermanagement.service.StripeService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class StripeServiceImpl implements StripeService {

    @Value("${stripe.key.secret}")
    String secretKey;

    @Override
    public Map<String, String> paymentSheetParams(Float amount) throws StripeException {
        Stripe.apiKey = this.secretKey;
        CustomerCreateParams customerParams = CustomerCreateParams.builder().build();
        Customer customer = Customer.create(customerParams);

        EphemeralKeyCreateParams ephemeralKeyParams =
                EphemeralKeyCreateParams.builder()
                        .setCustomer(customer.getId())
                        .build();

        RequestOptions ephemeralKeyOptions =
                new RequestOptions.RequestOptionsBuilder()
                        .setStripeVersionOverride("2020-08-27")
                        .build();

        EphemeralKey ephemeralKey = EphemeralKey.create(
                ephemeralKeyParams,
                ephemeralKeyOptions);

        PaymentIntentCreateParams paymentIntentParams =
                PaymentIntentCreateParams.builder()
                        .setAmount((long)(amount * 100))
                        .setCurrency("mkd")
                        .setCustomer(customer.getId())
                        .build();
        PaymentIntent paymentIntent = PaymentIntent.create(paymentIntentParams);

        Map<String, String> responseData = new HashMap<>();
        responseData.put("paymentIntent", paymentIntent.getClientSecret());
        responseData.put("ephemeralKey", ephemeralKey.getSecret());
        responseData.put("customer", customer.getId());

        return responseData;
    }
}
