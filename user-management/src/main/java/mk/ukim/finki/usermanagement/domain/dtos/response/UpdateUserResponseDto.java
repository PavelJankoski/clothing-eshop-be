package mk.ukim.finki.usermanagement.domain.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserResponseDto implements Serializable {
    private String fullName;

    private String imageUrl;
}
