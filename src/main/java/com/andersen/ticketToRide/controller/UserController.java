package com.andersen.ticketToRide.controller;

import com.andersen.ticketToRide.dto.UserDto;
import com.andersen.ticketToRide.service.UserService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@RequestMapping("/api/v1/user")
@Controller
@AllArgsConstructor
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    @GetMapping("/{username}")
    public String userProfile(@PathVariable String username, Model model, Principal principal) {
        LOGGER.info("GET request to /api/v1/user/username");

        userService.getUserInfo(model, principal);

        return "userPage";
    }

    @GetMapping("/success_after_creating_user")
    public String redirect(RedirectAttributes redirectAttributes) {
        LOGGER.info("GET request to /api/v1/user/success_after_creating_user");

        redirectAttributes.addFlashAttribute("successMessage", "User was successfully created");

        return "redirect:/auth/login";
    }

    @PostMapping("/creating")
    public ResponseEntity<?> registrationSubmit(@ModelAttribute UserDto userDto) {
        LOGGER.info("POST request to /api/v1/user/creating");

        userService.saveUser(userDto);

        return ResponseEntity.ok().body(userDto);
    }
}