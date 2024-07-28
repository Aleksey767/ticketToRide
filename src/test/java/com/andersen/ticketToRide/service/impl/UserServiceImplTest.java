package com.andersen.ticketToRide.service.impl;

import com.andersen.ticketToRide.dto.UserDto;
import com.andersen.ticketToRide.mapper.UserMapper;
import com.andersen.ticketToRide.model.User;
import com.andersen.ticketToRide.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserServiceImpl userService;

    private User user;

    private UserDto userDto;

    private String username;

    @BeforeEach
    void init() {
        userDto = mock(UserDto.class);
        user = mock(User.class);
        username = "Boris";
        userDto.setPassword("plainPassword");
    }



    @Test
    void getUserByUsername_shouldReturnUserDto() {
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));
        when(userMapper.toUserDto(user)).thenReturn(userDto);

        UserDto result = userService.getUserByUsername(username);

        assertEquals(userDto, result);
    }

    @Test
    void getUserByUsername_shouldCallRepository() {
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));

        userService.getUserByUsername(username);

        verify(userRepository).findByUsername(username);
    }

    @Test
    void updateUserBalance_shouldCallRepository() {
        BigDecimal balance = new BigDecimal("100.00");
        userService.updateUserBalance(username, balance);
        verify(userRepository).updateBalance(username, balance);
    }
}