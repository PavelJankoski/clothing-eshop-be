package mk.ukim.finki.usermanagement.domain.mappers;

import mk.ukim.finki.usermanagement.domain.dtos.response.UserInfoDto;
import mk.ukim.finki.usermanagement.domain.models.Person;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PersonToUserInfoDtoMapper {
    public UserInfoDto toGetUserInfoDto(Person person) {
        UserInfoDto dto = new UserInfoDto();
        dto.setId(person.getId());
        dto.setEmail(person.getEmail());
        dto.setImage(person.getImage() != null ? person.getImage().getUrl() : "");
        dto.setName(person.getFullName().getFirstName());
        dto.setSurname(person.getFullName().getLastName());
        dto.setPhoneNumber(person.getPhoneNumber());

        return dto;
    }
}