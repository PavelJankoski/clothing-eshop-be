package mk.ukim.finki.usermanagement.web;

import mk.ukim.finki.usermanagement.domain.dtos.request.LoginDto;
import mk.ukim.finki.usermanagement.domain.dtos.request.RegisterDto;
import mk.ukim.finki.usermanagement.domain.dtos.request.TokenDto;
import mk.ukim.finki.usermanagement.domain.dtos.request.UpdateUserRequestDto;
import mk.ukim.finki.usermanagement.domain.dtos.response.JwtDto;
import mk.ukim.finki.usermanagement.domain.dtos.response.UpdateUserResponseDto;
import mk.ukim.finki.usermanagement.domain.models.Person;
import mk.ukim.finki.usermanagement.service.PersonService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping(value = "/persons")
@CrossOrigin(value = "*")
public class PersonController {
    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
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

    @PatchMapping("/{userId}")
    public ResponseEntity<UpdateUserResponseDto> updateUserInfo(@PathVariable Long userId,
                                                                @RequestPart(required = false) MultipartFile image,
                                                                @RequestPart String name,
                                                                @RequestPart String surname,
                                                                @RequestPart String phoneNumber) {
        UpdateUserRequestDto dto = new UpdateUserRequestDto(name, surname, phoneNumber, image);
        return ResponseEntity.ok(this.personService.updateUserInfo(dto, userId));
    }
}
