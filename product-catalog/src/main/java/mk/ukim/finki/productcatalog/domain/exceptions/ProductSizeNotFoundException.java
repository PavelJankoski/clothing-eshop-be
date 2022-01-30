package mk.ukim.finki.productcatalog.domain.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ProductSizeNotFoundException extends RuntimeException{
    public ProductSizeNotFoundException(Long productId, Long sizeId){
        super(String.format("Product size for product with id: %s and size with id: %s is not found", productId, sizeId));
    }
}
