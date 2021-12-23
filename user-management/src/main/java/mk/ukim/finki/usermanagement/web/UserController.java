package mk.ukim.finki.usermanagement.web;

import mk.ukim.finki.usermanagement.domain.dtos.request.RegisterDto;
import mk.ukim.finki.usermanagement.domain.dtos.request.TokenDto;
import mk.ukim.finki.usermanagement.domain.dtos.request.UpdateUserRequestDto;
import mk.ukim.finki.usermanagement.domain.dtos.response.JwtDto;
import mk.ukim.finki.usermanagement.domain.dtos.response.UpdateUserResponseDto;
import mk.ukim.finki.usermanagement.domain.dtos.response.UserInfoDto;
import mk.ukim.finki.usermanagement.domain.models.User;
import mk.ukim.finki.usermanagement.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping(value = "/persons")
@CrossOrigin(value = "*")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping(value = "/oauth/google")
    public ResponseEntity<JwtDto> signInGoogle(@RequestBody TokenDto dto) throws IOException {
        return ResponseEntity.ok(this.userService.loginWithGoogle(dto));
    }

    @PostMapping(value = "/oauth/facebook")
    public ResponseEntity<JwtDto> signInFacebook(@RequestBody TokenDto dto) {
        return ResponseEntity.ok(this.userService.loginWithFacebook(dto));
    }

    @PostMapping(value = "/register")
    public ResponseEntity<User> register(@RequestBody RegisterDto dto) {
         return ResponseEntity.ok(this.userService.register(dto));
    }

    @GetMapping(value = "/{userId}")
    public ResponseEntity<UserInfoDto> getUserInfo(@PathVariable Long userId) {
        return ResponseEntity.ok(this.userService.getUserInfo(userId));
    }

    @PatchMapping("/{userId}")
    public ResponseEntity<UpdateUserResponseDto> updateUserInfo(@PathVariable Long userId,
                                                                @RequestPart(required = false) MultipartFile image,
                                                                @RequestPart String name,
                                                                @RequestPart String surname,
                                                                @RequestPart String phoneNumber) {
        UpdateUserRequestDto dto = new UpdateUserRequestDto(name, surname, phoneNumber, image);
        return ResponseEntity.ok(this.userService.updateUserInfo(dto, userId));
    }
}
