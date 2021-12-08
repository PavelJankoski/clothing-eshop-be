package mk.ukim.finki.usermanagement.domain.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class PersonNotFoundException extends RuntimeException{
    public PersonNotFoundException(String email){
        super(String.format("Person with email: %s is not found", email));
    }
    public PersonNotFoundException(Long id){
        super(String.format("Person with id: %s is not found", id));
    }
}