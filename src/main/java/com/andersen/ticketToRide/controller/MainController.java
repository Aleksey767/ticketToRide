package com.andersen.ticketToRide.controller;

import com.andersen.ticketToRide.dto.TicketDto;
import com.andersen.ticketToRide.dto.UserDto;
import com.andersen.ticketToRide.enums.Cities;
import com.andersen.ticketToRide.service.UserService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@AllArgsConstructor
public class MainController {

    private static final Logger LOGGER = LoggerFactory.getLogger(MainController.class);

    private final UserService userService;

    @GetMapping("/main")
    public String buyTicket(Model model, Principal principal) {
        LOGGER.info("GET request to /main");

        getUserInfo(model, principal);
        model.addAttribute("ticketDto",new TicketDto());
        model.addAttribute("cities", Cities.values());
        model.addAttribute("numbers", IntStream.rangeClosed(1, 10).mapToObj(BigDecimal::valueOf).collect(Collectors.toList()));
        return "main";
    }

    @GetMapping("/redirect_to_main")
    public String invalidData(@RequestParam String status, RedirectAttributes redirectAttributes) {
        LOGGER.info("GET request to /redirect_to_main");

        if (status.equals("invalid_data")) {
            redirectAttributes.addFlashAttribute("errorMessage", "You should pick arrival,departure and traveller amount");
        } else if (status.equals("out_of_money")) {
            redirectAttributes.addFlashAttribute("errorMessage", "You don't have enough money");
        } else {
            redirectAttributes.addFlashAttribute("successMessage", "Ticket was successfully purchased");
        }

        return "redirect:/main";
    }

    private void getUserInfo(Model model, Principal principal) {
        if (principal != null) {
            UserDto userDto = userService.getUserByUsername(principal.getName());
            model.addAttribute("balance", userDto.getBalance());
        }
    }
}