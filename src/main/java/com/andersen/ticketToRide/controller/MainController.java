package com.andersen.ticketToRide.controller;

import com.andersen.ticketToRide.dto.TicketDto;
import com.andersen.ticketToRide.dto.UserDto;
import com.andersen.ticketToRide.enums.Cities;
import com.andersen.ticketToRide.mapper.UserMapper;
import com.andersen.ticketToRide.service.TicketService;
import com.andersen.ticketToRide.service.UserService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.security.Principal;

@Controller
@AllArgsConstructor
public class MainController {

    private static final Logger logger = LoggerFactory.getLogger("com.andersen.ticketToRide");

    private final TicketService ticketService;

    private final UserService userService;

    @GetMapping("/")
    public String redirectToMain() {
        logger.info("[INFO MESSAGE]: GET request to /");
        return "redirect:/main";
    }

    @GetMapping("/main")
    public String showDropdown(Model model, @RequestParam(value = "selectedArrival", required = false) Cities selectedArrival,
                               @RequestParam(value = "selectedDeparture", required = false) Cities selectedDeparture) {
        logger.info("[INFO MESSAGE]: GET request to /main");
        model.addAttribute("cities", Cities.values());
        model.addAttribute("selectedArrival", selectedArrival);
        model.addAttribute("selectedDeparture", selectedDeparture);
        return "main";
    }

    @PostMapping("/main")
    public String selectCity(Model model, @RequestParam(value = "cityDeparture", required = false) Cities cityDeparture,
                             @RequestParam(value = "cityArrival", required = false) Cities cityArrival) {
        logger.info("[INFO MESSAGE]: POST request to /main");
        model.addAttribute("cities", Cities.values());
        model.addAttribute("selectedArrival", cityArrival);
        model.addAttribute("selectedDeparture", cityDeparture);
        return "main";
    }

    @Transactional
    @GetMapping("/{username}")
    public String userProfile(@PathVariable String username, Model model, Principal principal) {
        logger.info("[INFO MESSAGE]: GET request to /username");
        UserDto userDto = userService.getUserByUsername(principal.getName());
        model.addAttribute("tickets", ticketService.getAllTicketsByUser(userDto));
        model.addAttribute("balance", userDto.getBalance());
        return "userPage";
    }

    @Transactional
    @PostMapping("/add_ticket")
    public String addTicket(@ModelAttribute TicketDto ticketDto, Principal principal, Model model) {
        logger.info("[INFO MESSAGE]: POST request to /add_ticket");
        String username = principal.getName();
        if (ticketDto.getDeparture() == null || ticketDto.getArrival() == null || ticketDto.getArrival().equals(ticketDto.getDeparture())) {
            model.addAttribute("errorMessage", "Please choose arrival and departure");
            model.addAttribute("cities", Cities.values());
            return "main";
        }
        UserDto userDto = userService.getUserByUsername(username);
        ticketDto.setUser(UserMapper.mapToUser(userDto));
        ticketService.saveTicket(ticketDto);

        BigDecimal result = userDto.getBalance().subtract(BigDecimal.TEN);// stub!
        userService.updateUserBalance(username, result);

        model.addAttribute("successMessage", "Ticket was successfully purchased!");
        model.addAttribute("cities", Cities.values());
        return "main";
    }
}