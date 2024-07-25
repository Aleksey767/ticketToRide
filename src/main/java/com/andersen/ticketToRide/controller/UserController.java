package com.andersen.ticketToRide.controller;

import com.andersen.ticketToRide.dto.UserDto;
import com.andersen.ticketToRide.service.TicketService;
import com.andersen.ticketToRide.service.UserService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
@AllArgsConstructor
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger("com.andersen.ticketToRide");

    private final TicketService ticketService;

    private final UserService userService;

    @Transactional
    @GetMapping("/{username}")
    public String userProfile(@PathVariable String username, Model model, Principal principal) {
        LOGGER.info("[INFO MESSAGE]: GET request to /username");

        UserDto userDto = userService.getUserByUsername(principal.getName());

        model.addAttribute("tickets", ticketService.getAllTicketsByUser(userDto));
        model.addAttribute("balance", userDto.getBalance());
        return "userPage";
    }

    @GetMapping("/success_after_creating_user")
    public String redirect(RedirectAttributes redirectAttributes) {
        LOGGER.info("GET request to /success_after_creating_user");

        redirectAttributes.addFlashAttribute("successMessage", "User was successfully created");
        return "redirect:/login";
    }

    @PostMapping("/api/user/save_user")
    public ResponseEntity<?> registrationSubmit(@ModelAttribute UserDto userDto) {
        LOGGER.info("POST request to /api/user/save_user");
        userService.saveUser(userDto);

        return ResponseEntity.ok().body(userDto);
    }
}