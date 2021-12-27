package mk.ukim.finki.productcatalog.domain.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetUserInfoDto implements Serializable {
    private String image;

    private String name;

    private String surname;
}
