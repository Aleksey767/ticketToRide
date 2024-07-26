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

/**
 * Implementation of the {@link UserService} interface that handles user management tasks.
 * This class interacts with the {@link UserRepository} for persistence operations and uses
 * {@link BCryptPasswordEncoder} to handle password encryption.
 */
@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger("com.andersen.ticketToRide");

    private final UserMapper userMapper;

    /**
     * Provides a {@link BCryptPasswordEncoder} instance used for encoding passwords.
     *
     * @return a {@link BCryptPasswordEncoder} instance
     */
    private BCryptPasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Saves a user to the repository.
     *
     * This method encodes the user's password before persisting the user's information.
     *
     * @param userDto a {@link UserDto} object containing the details of the user to be saved
     */
    @Override
    public void saveUser(UserDto userDto) {
        LOGGER.debug("Saving user...");

        userDto.setPassword(encoder().encode(userDto.getPassword()));
        userRepository.save(userMapper.toUser(userDto));
    }

    /**
     * Retrieves a user by their username.
     *
     * This method fetches the user's details from the repository and maps them to a {@link UserDto} object.
     *
     * @param username the username of the user to retrieve
     * @return a {@link UserDto} object containing the user's details
     */
    @Override
    public UserDto getUserByUsername(String username) {
        LOGGER.debug("Getting user by username...");

        return userMapper.toUserDto(userRepository.findByUsername(username).get());
    }

    /**
     * Updates the balance of a user identified by their username.
     *
     * This method modifies the balance of the specified user in the repository.
     *
     * @param username the username of the user whose balance is to be updated
     * @param balance  the new balance to be set for the user
     */
    @Override
    public void updateUserBalance(String username, BigDecimal balance) {
        LOGGER.debug("Updating user balance");

        userRepository.updateBalance(username, balance);
    }
}