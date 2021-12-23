package mk.ukim.finki.usermanagement.domain.mappers;

import mk.ukim.finki.usermanagement.domain.dtos.response.UserInfoDto;
import mk.ukim.finki.usermanagement.domain.models.User;
import org.springframework.stereotype.Component;

@Component
public class UserToUserInfoDtoMapper {
    public UserInfoDto toGetUserInfoDto(User user) {
        UserInfoDto dto = new UserInfoDto();
        dto.setId(user.getId());
        dto.setEmail(user.getEmail());
        dto.setImage(user.getImage() != null ? user.getImage().getUrl() : "");
        dto.setName(user.getFullName().getFirstName());
        dto.setSurname(user.getFullName().getLastName());
        dto.setPhoneNumber(user.getPhoneNumber());

        return dto;
    }
}