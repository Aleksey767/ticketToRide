package com.andersen.ticketToRide.controller;

import com.andersen.ticketToRide.dto.UserDto;
import com.andersen.ticketToRide.service.UserService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@AllArgsConstructor
@Controller
public class AuthController {

    private static final Logger LOGGER = LoggerFactory.getLogger("com.andersen.ticketToRide");

    UserService userService;

    @GetMapping("/login")
    public String myLogin() {
        LOGGER.info("[INFO MESSAGE]: GET request to /login");
        return "login";
    }

    @GetMapping("/registration")
    public String registration(Model model) {
        LOGGER.info("[INFO MESSAGE]: GET request to /registration");
        model.addAttribute("user", new UserDto());
        return "registration";
    }

    @PostMapping("/api/user/save_user")
    public String registrationSubmit(@ModelAttribute("user") UserDto userDto, RedirectAttributes redirectAttributes) {
        LOGGER.info("[INFO MESSAGE]: POST request to /api/user/save_user");
        userService.saveUser(userDto);

        redirectAttributes.addFlashAttribute("successMessage", "A new user was successfully created");
        return "redirect:/login";
    }
}