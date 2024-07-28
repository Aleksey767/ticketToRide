package com.andersen.ticketToRide.security;

import com.andersen.ticketToRide.model.User;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * Implementation of the {@link UserDetails} interface, providing core user information.
 * This class is used by Spring Security to manage user authentication and authorization.
 */
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@EqualsAndHashCode
public class UserDetailsImpl implements UserDetails {

    private long id;

    private String username;

    private String password;

    private Collection<? extends GrantedAuthority> authorities;

    /**
     * Builds a {@link UserDetailsImpl} instance from a {@link User} entity.
     *
     * @param user the {@link User} entity
     * @return a {@link UserDetailsImpl} instance
     */
    public static UserDetailsImpl build(User user) {
        List<GrantedAuthority> authorityList = List.of(new SimpleGrantedAuthority(user.getRole().name()));
        return new UserDetailsImpl(
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                authorityList);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
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
}