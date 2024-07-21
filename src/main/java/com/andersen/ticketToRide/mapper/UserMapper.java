package com.andersen.ticketToRide.mapper;

import com.andersen.ticketToRide.dto.UserDto;
import com.andersen.ticketToRide.model.User;

public class UserMapper {

    public static User mapToUser(UserDto userDto) {
        return User.builder()
                .id(userDto.getId())
                .username(userDto.getUsername())
                .password(userDto.getPassword())
                .role(userDto.getRole())
                .balance(userDto.getBalance())
                .realName(userDto.getRealName())
                .build();
    }

    public static UserDto mapToUserDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .password(user.getPassword())
                .role(user.getRole())
                .balance(user.getBalance())
                .realName(user.getRealName())
                .build();
    }
}