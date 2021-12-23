package mk.ukim.finki.usermanagement.service;

import mk.ukim.finki.usermanagement.domain.dtos.request.RegisterDto;
import mk.ukim.finki.usermanagement.domain.dtos.request.TokenDto;
import mk.ukim.finki.usermanagement.domain.dtos.request.UpdateUserRequestDto;
import mk.ukim.finki.usermanagement.domain.dtos.response.JwtDto;
import mk.ukim.finki.usermanagement.domain.dtos.response.UpdateUserResponseDto;
import mk.ukim.finki.usermanagement.domain.dtos.response.UserInfoDto;
import mk.ukim.finki.usermanagement.domain.models.User;

import javax.validation.constraints.NotNull;
import java.io.IOException;

public interface UserService {
    User findPersonById(Long id);

    User register(RegisterDto dto);

    User findPersonByEmail(String email);

    JwtDto loginWithGoogle(TokenDto googleToken) throws IOException;

    JwtDto loginWithFacebook(TokenDto facebookToken);

    UpdateUserResponseDto updateUserInfo(UpdateUserRequestDto dto, Long userId);

    UserInfoDto getUserInfo(@NotNull Long userId);
}
