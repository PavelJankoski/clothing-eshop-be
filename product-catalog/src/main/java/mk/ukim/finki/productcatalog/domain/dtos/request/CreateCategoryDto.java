package mk.ukim.finki.productcatalog.domain.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateCategoryDto {
    private Integer typeId;
    private String name;
    private MultipartFile image;
}
