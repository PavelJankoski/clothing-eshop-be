package mk.ukim.finki.usermanagement.domain.valueobjects;

import lombok.*;

import javax.persistence.Embeddable;

@Embeddable
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class FullName {
    private String firstName;

    private String lastName;
}
