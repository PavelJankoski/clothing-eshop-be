package mk.ukim.finki.productcatalog.domain.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class GetCategoryDto implements Serializable {
    private Long id;

    private String name;

    private String type;

    private String imageUrl;
}
