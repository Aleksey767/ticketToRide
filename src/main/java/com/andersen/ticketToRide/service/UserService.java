package com.andersen.ticketToRide.service;

import com.andersen.ticketToRide.dto.UserDto;

public interface UserService {

    void saveUser(UserDto userDto);
    UserDto getUserById(Long id);
    UserDto getUserByUsername(String username);
}