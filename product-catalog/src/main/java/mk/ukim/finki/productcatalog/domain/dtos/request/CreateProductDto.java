package mk.ukim.finki.productcatalog.domain.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mk.ukim.finki.productcatalog.domain.models.Brand;
import mk.ukim.finki.productcatalog.domain.models.Category;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateProductDto implements Serializable {
    private String name;
    private String description;
    private Float price;
    private String code;
    private Brand brand;
    private Long categoryId;
}
