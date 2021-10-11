package mk.ukim.finki.usermanagement.service.impl;

import mk.ukim.finki.usermanagement.domain.dtos.request.LoginDto;
import mk.ukim.finki.usermanagement.domain.dtos.request.RegisterDto;
import mk.ukim.finki.usermanagement.domain.dtos.response.JwtDto;
import mk.ukim.finki.usermanagement.domain.enums.RoleType;
import mk.ukim.finki.usermanagement.domain.exceptions.PersonNotFoundException;
import mk.ukim.finki.usermanagement.domain.exceptions.UserDeletedException;
import mk.ukim.finki.usermanagement.domain.models.Person;
import mk.ukim.finki.usermanagement.domain.models.Role;
import mk.ukim.finki.usermanagement.domain.valueobjects.FullName;
import mk.ukim.finki.usermanagement.repository.PersonRepository;
import mk.ukim.finki.usermanagement.security.jwt.JwtUtils;
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
    private final JwtUtils jwtUtils;


    public PersonServiceImpl(PasswordEncoder passwordEncoder, PersonRepository personRepository, RoleService roleService, AuthenticationManager authenticationManager, JwtUtils jwtUtils) {
        this.passwordEncoder = passwordEncoder;
        this.personRepository = personRepository;
        this.roleService = roleService;
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
    }

    @Override
    public JwtDto login(LoginDto dto) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        JwtDto jwtDto = new JwtDto();
        jwtDto.setAccessToken(jwt);
        jwtDto.setEmail(dto.getEmail());
        jwtDto.setFullName(userDetails.getFullName());
        jwtDto.setUserId(userDetails.getUserId());
        jwtDto.setRole(roles.get(0));

        return jwtDto;
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
