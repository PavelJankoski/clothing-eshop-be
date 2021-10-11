package mk.ukim.finki.usermanagement.service.impl;

import mk.ukim.finki.usermanagement.domain.dtos.request.LoginDto;
import mk.ukim.finki.usermanagement.domain.dtos.request.RegisterDto;
import mk.ukim.finki.usermanagement.domain.dtos.response.JwtDto;
import mk.ukim.finki.usermanagement.domain.enums.RoleType;
import mk.ukim.finki.usermanagement.domain.exceptions.PersonNotFoundException;
import mk.ukim.finki.usermanagement.domain.models.Person;
import mk.ukim.finki.usermanagement.domain.models.Role;
import mk.ukim.finki.usermanagement.domain.valueobjects.FullName;
import mk.ukim.finki.usermanagement.repository.PersonRepository;
import mk.ukim.finki.usermanagement.security.services.UserDetailsImpl;
import mk.ukim.finki.usermanagement.service.PersonService;
import mk.ukim.finki.usermanagement.service.RoleService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PersonServiceImpl implements PersonService {

    private final PasswordEncoder passwordEncoder;
    private final PersonRepository personRepository;
    private final RoleService roleService;
    private final AuthenticationManager authenticationManager;


    public PersonServiceImpl(PasswordEncoder passwordEncoder, PersonRepository personRepository, RoleService roleService, AuthenticationManager authenticationManager) {
        this.passwordEncoder = passwordEncoder;
        this.personRepository = personRepository;
        this.roleService = roleService;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public JwtDto login(LoginDto dto) {

        return null;
    }

    @Override
    public Person register(RegisterDto dto) {
        String encodedPassword = passwordEncoder.encode(dto.getPassword());
        Role role = this.roleService.findRoleByType(RoleType.ROLE_USER);
        Person person = new Person(new FullName(dto.getFirstName(), dto.getLastName()), dto.getEmail(), encodedPassword, dto.getPhoneNumber(), role, null);

        return this.personRepository.save(person);
    }

    @Override
    public Person findPersonByEmail(String email) {
        return this.personRepository.findPersonByEmailAndIsDeletedFalse(email).orElseThrow(() -> new PersonNotFoundException(email));
    }
}
