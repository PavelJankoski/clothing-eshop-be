package mk.ukim.finki.usermanagement.security.services;

import mk.ukim.finki.usermanagement.domain.models.User;
import mk.ukim.finki.usermanagement.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = this.userRepository.findPersonByEmailAndIsDeletedFalse(email)
                .orElseThrow(() -> new UsernameNotFoundException(email));

        return UserDetailsImpl.build(user);
    }
}