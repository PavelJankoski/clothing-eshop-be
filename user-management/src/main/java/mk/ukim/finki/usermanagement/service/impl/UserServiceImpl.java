package mk.ukim.finki.usermanagement.service.impl;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import lombok.RequiredArgsConstructor;
import mk.ukim.finki.usermanagement.domain.dtos.request.RegisterDto;
import mk.ukim.finki.usermanagement.domain.dtos.request.TokenDto;
import mk.ukim.finki.usermanagement.domain.dtos.request.UpdateUserRequestDto;
import mk.ukim.finki.usermanagement.domain.dtos.response.JwtDto;
import mk.ukim.finki.usermanagement.domain.dtos.response.UpdateUserResponseDto;
import mk.ukim.finki.usermanagement.domain.dtos.response.UserInfoDto;
import mk.ukim.finki.usermanagement.domain.enums.RoleType;
import mk.ukim.finki.usermanagement.domain.exceptions.PersonNotFoundException;
import mk.ukim.finki.usermanagement.domain.mappers.UserToUserInfoDtoMapper;
import mk.ukim.finki.usermanagement.domain.models.Image;
import mk.ukim.finki.usermanagement.domain.models.User;
import mk.ukim.finki.usermanagement.domain.models.Role;
import mk.ukim.finki.usermanagement.domain.valueobjects.FullName;
import mk.ukim.finki.usermanagement.repository.UserRepository;
import mk.ukim.finki.usermanagement.service.ImageService;
import mk.ukim.finki.usermanagement.service.UserService;
import mk.ukim.finki.usermanagement.service.RoleService;
import mk.ukim.finki.usermanagement.util.CommonFunctions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.impl.FacebookTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.Collections;
import java.util.LinkedHashMap;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    @Value("${oauth.google-client-id}")
    private String googleClientId;

    @Value("${client_id}")
    private String clientId;

    @Value("${client_credential}")
    private String clientSecret;

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RoleService roleService;
    private final ImageService imageService;
    private final UserToUserInfoDtoMapper userToUserInfoDtoMapper;


    @Override
    public User findPersonById(Long id) {
        return this.userRepository.findById(id).orElseThrow(() -> new PersonNotFoundException(id));
    }

    @Override
    public User register(RegisterDto dto) {
        String encodedPassword = passwordEncoder.encode(dto.getPassword());
        Role role = this.roleService.findRoleByType(RoleType.ROLE_USER);
        User user = new User(new FullName(dto.getFirstName(), dto.getLastName()), dto.getEmail(), encodedPassword, dto.getPhoneNumber(), role, null);

        return this.userRepository.save(user);
    }

    @Override
    public User findPersonByEmail(String email) {
        return this.userRepository.findPersonByEmailAndIsDeletedFalse(email).orElseThrow(() -> new PersonNotFoundException(email));
    }

    @Override
    public JwtDto loginWithGoogle(TokenDto googleToken) throws IOException {
        NetHttpTransport transport = new NetHttpTransport();
        JacksonFactory jacksonFactory = JacksonFactory.getDefaultInstance();
        GoogleIdTokenVerifier.Builder verifier =
                new GoogleIdTokenVerifier.Builder(transport, jacksonFactory)
                        .setAudience(Collections.singletonList(googleClientId));
        GoogleIdToken googleIdToken = GoogleIdToken.parse(verifier.getJsonFactory(), googleToken.getToken());
        GoogleIdToken.Payload payload = googleIdToken.getPayload();
        String password = createOrUpdateSocialUser(payload.getEmail(), (String) payload.get("given_name"), (String) payload.get("family_name"), (String) payload.get("picture"));
        return loginUser(payload.getEmail(), password);
    }

    @Override
    public JwtDto loginWithFacebook(TokenDto facebookToken) {
        Facebook facebook = new FacebookTemplate(facebookToken.getToken());
        final String [] fields = {"email", "picture", "name"};
        org.springframework.social.facebook.api.User user =  facebook.fetchObject("me", org.springframework.social.facebook.api.User.class, fields);
        LinkedHashMap linkedHashMap = (LinkedHashMap) user.getExtraData().get("picture");
        LinkedHashMap linkedHashMap1 = (LinkedHashMap) linkedHashMap.get("data");
        String [] name = user.getName().split("\\s+");
        String password = createOrUpdateSocialUser(user.getEmail(), name[0], name[1], (String) linkedHashMap1.get("url"));
        return loginUser(user.getEmail(), password);
    }

    @Override
    public UpdateUserResponseDto updateUserInfo(UpdateUserRequestDto dto, Long userId) {
        User user = this.findPersonById(userId);
        Image image = user.getImage();
        user.setFullName(new FullName(dto.getName(), dto.getSurname()));
        user.setPhoneNumber(dto.getPhoneNumber());
        if(dto.getImage() != null) {
            try {
                image = this.imageService.save(this.imageService.uploadForUrl(dto.getImage()));
            } catch (IOException e) {
                e.printStackTrace();
                image = user.getImage();
            }
            user.setImage(image);
        }
        this.userRepository.save(user);
        return new UpdateUserResponseDto(user.getFormattedFullName(), image != null ? image.getUrl() : "");
    }

    @Override
    public UserInfoDto getUserInfo(Long userId) {
        return this.userToUserInfoDtoMapper.toGetUserInfoDto(this.findPersonById(userId));
    }

    @Transactional
    String createOrUpdateSocialUser(String email, String firstName, String lastName, String imageUrl) {
        String password = CommonFunctions.alphaNumericString(15);
        User user;
        if(!this.userRepository.existsByEmailAndIsDeletedFalse(email)) {
            Image image = this.imageService.save(imageUrl);
            user = new User(new FullName(firstName, lastName), email, passwordEncoder.encode(password), "", this.roleService.findRoleByType(RoleType.ROLE_USER), image);
        }
        else {
            user = this.userRepository.findPersonByEmailAndIsDeletedFalse(email).orElseThrow(() -> new PersonNotFoundException(email));
            user.setPassword(passwordEncoder.encode(password));
        }
        this.userRepository.saveAndFlush(user);
        return password;
    }

    private JwtDto loginUser(String email, String password) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map= new LinkedMultiValueMap<String, String>();
        map.add("username", email);
        map.add("password", password);
        map.add("client_id", clientId);
        map.add("client_secret", clientSecret);
        map.add("grant_type", "password");

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);

        ResponseEntity<JwtDto> response = restTemplate.postForEntity( "http://localhost:8080/oauth/token", request , JwtDto.class );
        return response.getBody();
    }

}
