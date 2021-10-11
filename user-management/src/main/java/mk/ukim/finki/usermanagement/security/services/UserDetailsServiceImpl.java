package mk.ukim.finki.usermanagement.security.services;

import mk.ukim.finki.usermanagement.domain.models.Person;
import mk.ukim.finki.usermanagement.repository.PersonRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final PersonRepository personRepository;

    public UserDetailsServiceImpl(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Person user = this.personRepository.findPersonByEmailAndIsDeletedFalse(email)
                .orElseThrow(() -> new UsernameNotFoundException(email));

        return UserDetailsImpl.build(user);
    }
}