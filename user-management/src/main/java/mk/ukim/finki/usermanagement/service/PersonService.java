package mk.ukim.finki.usermanagement.service;

import mk.ukim.finki.usermanagement.domain.dtos.request.LoginDto;
import mk.ukim.finki.usermanagement.domain.dtos.request.RegisterDto;
import mk.ukim.finki.usermanagement.domain.dtos.response.JwtDto;
import mk.ukim.finki.usermanagement.domain.models.Person;

public interface PersonService {

    JwtDto login(LoginDto dto);

    Person register(RegisterDto dto);

    Person findPersonByEmail(String email);
}
