package com.andersen.ticketToRide.service;

import com.andersen.ticketToRide.dto.UserDto;

import java.math.BigDecimal;

public interface UserService {
    void saveUser(UserDto userDto);
    UserDto getUserByUsername(String username);
    void updateUserBalance(String username, BigDecimal balance);
}