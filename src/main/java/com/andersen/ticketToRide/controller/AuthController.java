package com.andersen.ticketToRide.controller;

import com.andersen.ticketToRide.dto.UserDto;
import com.andersen.ticketToRide.service.UserService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@AllArgsConstructor
@Controller
public class AuthController {

    private static final Logger LOGGER = LoggerFactory.getLogger("com.andersen.ticketToRide");

    UserService userService;

    @GetMapping("/login")
    public String myLogin() {
        LOGGER.info("GET request to /login");
        return "login";
    }

    @GetMapping("/registration")
    public String registration(Model model) {
        LOGGER.info("GET request to /registration");
        model.addAttribute("user", new UserDto());
        return "registration";
    }
}