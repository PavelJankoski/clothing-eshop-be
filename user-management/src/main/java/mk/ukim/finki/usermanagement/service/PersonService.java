package mk.ukim.finki.usermanagement.service;

import mk.ukim.finki.usermanagement.domain.dtos.request.LoginDto;
import mk.ukim.finki.usermanagement.domain.dtos.request.RegisterDto;
import mk.ukim.finki.usermanagement.domain.dtos.request.TokenDto;
import mk.ukim.finki.usermanagement.domain.dtos.response.JwtDto;
import mk.ukim.finki.usermanagement.domain.models.Person;

import java.io.IOException;

public interface PersonService {

    JwtDto login(LoginDto dto) throws IOException;

    Person register(RegisterDto dto);

    Person findPersonByEmail(String email);

    JwtDto loginWithGoogle(TokenDto googleToken) throws IOException;

    JwtDto loginWithFacebook(TokenDto facebookToken);
}
