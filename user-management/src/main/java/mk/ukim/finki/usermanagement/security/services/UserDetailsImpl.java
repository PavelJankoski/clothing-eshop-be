package mk.ukim.finki.usermanagement.security.services;

import com.fasterxml.jackson.annotation.JsonIgnore;
import mk.ukim.finki.usermanagement.domain.models.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UserDetailsImpl implements UserDetails {

    private final String email;

    @JsonIgnore
    private final String password;

    private final Long userId;

    private final Collection<? extends GrantedAuthority> authorities;

    public UserDetailsImpl(String email,
                           String password,
                           Long userId,
                           Collection<? extends GrantedAuthority> authorities){
        this.email = email;
        this.password = password;
        this.userId = userId;
        this.authorities = authorities;
    }

    public static UserDetailsImpl build(User user) {
        List<GrantedAuthority> authorityList = new ArrayList<>();
        authorityList.add(new SimpleGrantedAuthority(user.getRole().getType().name()));
        return new UserDetailsImpl(user.getEmail(), user.getPassword(), user.getId(), authorityList);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public Long getUserId() {
        return this.userId;
    }
}