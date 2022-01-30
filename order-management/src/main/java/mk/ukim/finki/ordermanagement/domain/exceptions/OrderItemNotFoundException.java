package mk.ukim.finki.ordermanagement.domain.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class OrderItemNotFoundException extends RuntimeException{
    public OrderItemNotFoundException(){
        super("Order item not found!");
    }
}
