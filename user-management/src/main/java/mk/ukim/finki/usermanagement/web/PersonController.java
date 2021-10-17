package mk.ukim.finki.usermanagement.web;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import lombok.Getter;
import mk.ukim.finki.usermanagement.domain.dtos.request.LoginDto;
import mk.ukim.finki.usermanagement.domain.dtos.request.RegisterDto;
import mk.ukim.finki.usermanagement.domain.dtos.request.TokenDto;
import mk.ukim.finki.usermanagement.domain.dtos.response.JwtDto;
import mk.ukim.finki.usermanagement.domain.models.Person;
import mk.ukim.finki.usermanagement.service.PersonService;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.ws.rs.core.SecurityContext;
import java.io.IOException;
import java.util.Collections;

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
        // UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword());
        Authentication sec = SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.ok(new JwtDto());
    }

    @PostMapping(value = "/oauth/google")
    public ResponseEntity<JwtDto> signInGoogle(@RequestBody TokenDto dto) throws IOException {
        return ResponseEntity.ok(this.personService.loginWithGoogle(dto));
    }

    @PostMapping(value = "/oauth/facebook")
    public ResponseEntity<JwtDto> signInFacebook(@RequestBody TokenDto dto) {
        return ResponseEntity.ok(this.personService.loginWithFacebook(dto));
    }

    @PostMapping(value = "/register")
    public ResponseEntity<Person> register(@RequestBody RegisterDto dto) {
         return ResponseEntity.ok(this.personService.register(dto));
    }
}
