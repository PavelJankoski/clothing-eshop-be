package mk.ukim.finki.usermanagement.domain.dtos.request;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserRequestDto implements Serializable {
    private String name;

    private String surname;

    private String phoneNumber;

    private MultipartFile image;
}
