package ru.spbpu.collections.model.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.spbpu.collections.entities.User;

import java.io.Serial;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@ToString(exclude = {"password"})
public class UserDetailsImpl implements UserDetails {

    @Serial
    private static final long serialVersionUID = 2L;

    private final Long id;
    private final String email;
    @JsonIgnore
    private String password;

    private final Collection<? extends GrantedAuthority> authorities;

    public UserDetailsImpl(final Long id, final String email, final String password,
                           final Collection<? extends GrantedAuthority> authorities) {

        this.id = id;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
    }

    public static UserDetailsImpl build(final User user) {

        List<GrantedAuthority> authorities = user.getRoles().stream()
                                                 .map(role -> new SimpleGrantedAuthority(role.getName()))
                                                 .collect(Collectors.toList());

        return new UserDetailsImpl(user.getId(),
                user.getEmail(),
                user.getPassword(),
                authorities);
    }

    @Override
    public String getUsername() {
        return email;
    }

}
