package com.andersen.ticketToRide.service.impl;

import com.andersen.ticketToRide.dto.UserDto;
import com.andersen.ticketToRide.mapper.UserMapper;
import com.andersen.ticketToRide.model.User;
import com.andersen.ticketToRide.repository.UserRepository;
import com.andersen.ticketToRide.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    private BCryptPasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void saveUser(UserDto userDto) {
        userDto.setPassword(encoder().encode(userDto.getPassword()));
        userRepository.save(UserMapper.mapToUser(userDto));
    }

    @Override
    public UserDto getUserById(Long id) {
        return UserMapper.mapToUserDto(userRepository.findById(id).get());
    }

    @Override
    public UserDto getUserByUsername(String username) {
        return UserMapper.mapToUserDto(userRepository.findByUsername(username).get());
    }
}
