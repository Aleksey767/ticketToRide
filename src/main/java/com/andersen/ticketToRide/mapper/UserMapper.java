package com.andersen.ticketToRide.mapper;

import com.andersen.ticketToRide.dto.UserDto;
import com.andersen.ticketToRide.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toUserDto(User user);
    User toUser(UserDto userDto);
}
