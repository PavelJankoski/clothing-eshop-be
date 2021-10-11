package mk.ukim.finki.usermanagement.security.services;

import com.fasterxml.jackson.annotation.JsonIgnore;
import mk.ukim.finki.usermanagement.domain.models.Person;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UserDetailsImpl implements UserDetails {

    private String email;

    @JsonIgnore
    private String password;

    private String fullName;

    private Long userId;

    private String phoneNumber;

    private Collection<? extends GrantedAuthority> authorities;

    public UserDetailsImpl(String email,
                           String password,
                           String fullName,
                           Long userId,
                           String phoneNumber,
                           Collection<? extends GrantedAuthority> authorities){
        this.email = email;
        this.password = password;
        this.fullName = fullName;
        this.userId = userId;
        this.phoneNumber = phoneNumber;
        this.authorities = authorities;
    }

    public static UserDetailsImpl build(Person user) {
        List<GrantedAuthority> authorityList = new ArrayList<>();
        authorityList.add(new SimpleGrantedAuthority(user.getRole().getType().name()));
        return new UserDetailsImpl(user.getEmail(), user.getPassword(), user.getFullName(), user.getId(), user.getPhoneNumber(), authorityList);
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

    public String getFullName() {
        return this.fullName;
    }

    public Long getUserId() {
        return this.userId;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }
}