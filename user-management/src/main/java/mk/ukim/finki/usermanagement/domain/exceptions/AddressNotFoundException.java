package mk.ukim.finki.usermanagement.domain.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class AddressNotFoundException extends RuntimeException{
    public AddressNotFoundException(Long id){
        super(String.format("Address with id: %s is not found", id));
    }
}