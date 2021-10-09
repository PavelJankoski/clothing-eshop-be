package mk.ukim.finki.usermanagement.domain.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UserDeletedException extends RuntimeException {
    public UserDeletedException(String email) {
        super(String.format("User with email %s is deleted", email));
    }
}
