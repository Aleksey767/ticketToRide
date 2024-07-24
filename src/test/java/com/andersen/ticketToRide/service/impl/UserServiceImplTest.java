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
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    private User user;

    private String username;

    private UserDto userDto;

    @BeforeEach
    void init() {
        userDto = mock(UserDto.class);
        user = mock(User.class);
        username = "Boris";
    }

    @Test
    void saveUser_shouldCallRepository() {
        when(userDto.getPassword()).thenReturn("password");

        try (MockedStatic<UserMapper> mockedStatic = Mockito.mockStatic(UserMapper.class)) {
            mockedStatic.when(() -> UserMapper.mapToUser(userDto)).thenReturn(user);

            userService.saveUser(userDto);

            verify(userRepository).save(user);
        }
    }

    @Test
    void getUserByUsername_shouldReturnUserDto() {
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));

        try (MockedStatic<UserMapper> mockedStatic = Mockito.mockStatic(UserMapper.class)) {
            mockedStatic.when(() -> UserMapper.mapToUserDto(user)).thenReturn(userDto);

            UserDto result = userService.getUserByUsername(username);

            assertEquals(userDto, result);
        }
    }

    @Test
    void getUserByUsername_shouldCallRepository() {
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));

        userService.getUserByUsername(username);

        verify(userRepository).findByUsername(username);
    }

    @Test
    void updateUserBalance_shouldCallRepository() {
        userService.updateUserBalance(username, user.getBalance());

        verify(userRepository).updateBalance(username, user.getBalance());
    }
}