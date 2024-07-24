package com.andersen.ticketToRide.security;

import com.andersen.ticketToRide.model.User;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.math.BigDecimal;
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
    private BigDecimal balance;
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
                user.getBalance(),
                authorityList);
    }

    /**
     * Returns the authorities granted to the user.
     *
     * @return a collection of {@link GrantedAuthority} objects
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    /**
     * Returns the password used to authenticate the user.
     *
     * @return the password
     */
    @Override
    public String getPassword() {
        return password;
    }

    /**
     * Returns the username used to authenticate the user.
     *
     * @return the username
     */
    @Override
    public String getUsername() {
        return username;
    }

    /**
     * Indicates whether the user's account has expired.
     *
     * @return {@code true} if the user's account is valid (i.e., non-expired), {@code false} if no longer valid (i.e., expired)
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * Indicates whether the user is locked or unlocked.
     *
     * @return {@code true} if the user is not locked, {@code false} otherwise
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * Indicates whether the user's credentials (password) have expired.
     *
     * @return {@code true} if the user's credentials are valid (i.e., non-expired), {@code false} if no longer valid (i.e., expired)
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * Indicates whether the user is enabled or disabled.
     *
     * @return {@code true} if the user is enabled, {@code false} otherwise
     */
    @Override
    public boolean isEnabled() {
        return true;
    }
}