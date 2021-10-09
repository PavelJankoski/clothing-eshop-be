package mk.ukim.finki.usermanagement.domain.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtDto implements Serializable {
    private String accessToken;

    private String tokenType = "Bearer";

    private String username;

    private String role;
}
