package mk.ukim.finki.usermanagement.domain.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoDto implements Serializable {
    private Long id;

    private String email;

    private String image;

    private String name;

    private String surname;

    private String phoneNumber;
}
