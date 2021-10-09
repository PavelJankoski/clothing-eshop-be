package mk.ukim.finki.usermanagement.domain.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mk.ukim.finki.usermanagement.domain.valueobjects.FullName;

import javax.persistence.Column;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterDto implements Serializable {
    private String firstName;

    private String lastName;

    private String email;

    private String password;

    private String phoneNumber;
}
