package mk.ukim.finki.usermanagement.service.impl;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import mk.ukim.finki.usermanagement.domain.dtos.request.LoginDto;
import mk.ukim.finki.usermanagement.domain.dtos.request.RegisterDto;
import mk.ukim.finki.usermanagement.domain.dtos.request.TokenDto;
import mk.ukim.finki.usermanagement.domain.dtos.response.JwtDto;
import mk.ukim.finki.usermanagement.domain.enums.RoleType;
import mk.ukim.finki.usermanagement.domain.exceptions.PersonNotFoundException;
import mk.ukim.finki.usermanagement.domain.models.Image;
import mk.ukim.finki.usermanagement.domain.models.Person;
import mk.ukim.finki.usermanagement.domain.models.Role;
import mk.ukim.finki.usermanagement.domain.valueobjects.FullName;
import mk.ukim.finki.usermanagement.repository.PersonRepository;
import mk.ukim.finki.usermanagement.service.ImageService;
import mk.ukim.finki.usermanagement.service.PersonService;
import mk.ukim.finki.usermanagement.service.RoleService;
import mk.ukim.finki.usermanagement.util.CommonFunctions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.User;
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
public class PersonServiceImpl implements PersonService {

    @Value("${oauth.google-client-id}")
    private String googleClientId;

    @Value("${oauth.client-id}")
    private String clientId;

    @Value("${oauth.client-secret}")
    private String clientSecret;

    private final PasswordEncoder passwordEncoder;
    private final PersonRepository personRepository;
    private final RoleService roleService;
    private final ImageService imageService;


    public PersonServiceImpl(PasswordEncoder passwordEncoder, PersonRepository personRepository, RoleService roleService, ImageService imageService) {
        this.passwordEncoder = passwordEncoder;
        this.personRepository = personRepository;
        this.roleService = roleService;
        this.imageService = imageService;
    }

    @Override
    public JwtDto login(LoginDto dto) throws IOException {

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
        User user =  facebook.fetchObject("me", User.class, fields);
        LinkedHashMap linkedHashMap = (LinkedHashMap) user.getExtraData().get("picture");
        LinkedHashMap linkedHashMap1 = (LinkedHashMap) linkedHashMap.get("data");
        String [] name = user.getName().split("\\s+");
        String password = createOrUpdateSocialUser(user.getEmail(), name[0], name[1], (String) linkedHashMap1.get("url"));
        return loginUser(user.getEmail(), password);
    }

    @Transactional
    String createOrUpdateSocialUser(String email, String firstName, String lastName, String imageUrl) {
        String password = CommonFunctions.alphaNumericString(15);
        Person person;
        if(!this.personRepository.existsByEmailAndIsDeletedFalse(email)) {
            Image image = this.imageService.save(imageUrl);
            person = new Person(new FullName(firstName, lastName), email, passwordEncoder.encode(password), "", this.roleService.findRoleByType(RoleType.ROLE_USER), image);
        }
        else {
            person = this.personRepository.findPersonByEmailAndIsDeletedFalse(email).orElseThrow(() -> new PersonNotFoundException(email));
            person.setPassword(passwordEncoder.encode(password));
        }
        this.personRepository.saveAndFlush(person);
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
