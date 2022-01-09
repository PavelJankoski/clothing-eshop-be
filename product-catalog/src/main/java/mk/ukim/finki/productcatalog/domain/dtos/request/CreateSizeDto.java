package mk.ukim.finki.productcatalog.domain.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateSizeDto implements Serializable {
    private String size;
    private Integer quantity;
}
