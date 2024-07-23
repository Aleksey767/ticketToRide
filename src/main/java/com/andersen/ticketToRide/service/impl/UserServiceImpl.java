package com.andersen.ticketToRide.service.impl;

import com.andersen.ticketToRide.dto.UserDto;
import com.andersen.ticketToRide.mapper.UserMapper;
import com.andersen.ticketToRide.repository.UserRepository;
import com.andersen.ticketToRide.service.UserService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    private BCryptPasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    private static final Logger LOGGER = LoggerFactory.getLogger("com.andersen.ticketToRide");

    @Override
    public void saveUser(UserDto userDto) {
        LOGGER.debug("[DEBUG MESSAGE]: Saving user...");
        userDto.setPassword(encoder().encode(userDto.getPassword()));
        userRepository.save(UserMapper.mapToUser(userDto));
    }

    @Override
    public UserDto getUserByUsername(String username) {
        LOGGER.debug("[DEBUG MESSAGE]: Getting user by username...");
        return UserMapper.mapToUserDto(userRepository.findByUsername(username).get());
    }

    @Override
    public void updateUserBalance(String username, BigDecimal balance) {
        LOGGER.debug("[DEBUG MESSAGE]: Updating user balance");
        userRepository.updateBalance(username, balance);
    }
}