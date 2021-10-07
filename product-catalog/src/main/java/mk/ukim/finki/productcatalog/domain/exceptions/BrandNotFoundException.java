package mk.ukim.finki.productcatalog.domain.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class BrandNotFoundException extends RuntimeException{
    public BrandNotFoundException(String id){
        super(String.format("Brand with id: %s is not found", id));
    }
}