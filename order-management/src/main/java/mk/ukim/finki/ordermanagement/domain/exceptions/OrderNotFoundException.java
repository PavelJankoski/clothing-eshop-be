package mk.ukim.finki.ordermanagement.domain.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class OrderNotFoundException extends RuntimeException{
    public OrderNotFoundException(Long orderId){
        super(String.format("Order with id: %s not found!", orderId));
    }
}
