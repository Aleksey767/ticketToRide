package com.andersen.ticketToRide.service;

import com.andersen.ticketToRide.dto.UserDto;

import java.math.BigDecimal;

/**
 * The {@code UserService} interface provides methods to manage user information in the ticket-to-ride application.
 * It allows for creating, retrieving, and updating user details.
 */
public interface UserService {

    /**
     * Saves a new user to the system.
     *
     * @param userDto a {@link UserDto} object containing the user details to be saved
     */
    void saveUser(UserDto userDto);

    /**
     * Retrieves a user by their username.
     *
     * @param username the username of the user to retrieve
     * @return a {@link UserDto} object containing the user details
     */
    UserDto getUserByUsername(String username);

    /**
     * Updates the balance of a user identified by their username.
     *
     * @param username the username of the user whose balance is to be updated
     * @param balance  the new balance to be set for the user
     */
    void updateUserBalance(String username, BigDecimal balance);
}
