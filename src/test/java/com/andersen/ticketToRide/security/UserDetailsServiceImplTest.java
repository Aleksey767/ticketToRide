package com.andersen.ticketToRide.security;

import com.andersen.ticketToRide.model.User;
import com.andersen.ticketToRide.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserDetailsServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserDetailsServiceImpl userDetailsService;

    @Test
    void loadUserByUsername_whenUserExists_shouldReturnUserDetailsImpl() {

        String username = "testUser";
        User user = new User();
        UserDetailsImpl userDetails = UserDetailsImpl.build(user);

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));


        UserDetailsImpl result = userDetailsService.loadUserByUsername(username);


        assertNotNull(result);
        assertEquals(userDetails, result);
        verify(userRepository).findByUsername(username);
    }

    @Test
    void loadUserByUsername_whenUserDoesNotExist_shouldThrowUsernameNotFoundException() {

        String username = "testUser";

        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());


        UsernameNotFoundException exception = assertThrows(UsernameNotFoundException.class,
                () -> userDetailsService.loadUserByUsername(username));
        assertEquals(username + " not found", exception.getMessage());
        verify(userRepository).findByUsername(username);
    }
}