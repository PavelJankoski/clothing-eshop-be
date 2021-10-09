package mk.ukim.finki.usermanagement.web;

import mk.ukim.finki.usermanagement.domain.dtos.request.LoginDto;
import mk.ukim.finki.usermanagement.domain.dtos.request.RegisterDto;
import mk.ukim.finki.usermanagement.domain.dtos.response.JwtDto;
import mk.ukim.finki.usermanagement.domain.models.Person;
import mk.ukim.finki.usermanagement.service.PersonService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/persons")
@CrossOrigin(value = "*")
public class PersonController {
    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @PostMapping(value = "/login")
    public ResponseEntity<JwtDto> signIn(@RequestBody LoginDto dto) {
        return ResponseEntity.ok(this.personService.login(dto));
    }

    @PostMapping(value = "/register")
    public ResponseEntity<Person> register(@RequestBody RegisterDto dto) {
        return ResponseEntity.ok(this.personService.register(dto));
    }
}
