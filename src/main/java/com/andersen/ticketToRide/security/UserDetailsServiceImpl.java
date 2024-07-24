package com.andersen.ticketToRide.security;

import com.andersen.ticketToRide.model.User;
import com.andersen.ticketToRide.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Implementation of the {@link UserDetailsService} interface that is used to retrieve user-related data.
 * This service is used by Spring Security to load user-specific data during the authentication process.
 */
@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    /**
     * Loads the user based on the username.
     * This method retrieves the {@link User} entity from the repository using the provided username
     * and builds a {@link UserDetailsImpl} object.
     *
     * @param username the username identifying the user whose data is required
     * @return a {@link UserDetailsImpl} object containing user details
     */
    @Override
    public UserDetailsImpl loadUserByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username + " not found"));
        return UserDetailsImpl.build(user);
    }
}