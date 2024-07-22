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

    private static final Logger LOGGER = LoggerFactory.getLogger(MainController.class);

    private final TicketService ticketService;
    private final UserService userService;

    @GetMapping("/main")
    public String buyTicket(Model model, Principal principal,
                            @RequestParam(value = "selectedArrival", required = false) Cities selectedArrival,
                            @RequestParam(value = "selectedDeparture", required = false) Cities selectedDeparture) {
        LOGGER.info("[INFO MESSAGE]: GET request to /main");

        addUserInfoToModel(model, principal);
        model.addAttribute("cities", Cities.values());
        model.addAttribute("selectedArrival", selectedArrival);
        model.addAttribute("selectedDeparture", selectedDeparture);
        return "main";
    }

    @PostMapping("/api/ticket/select_city")
    public String selectCity(Model model, Principal principal,
                             @RequestParam(value = "cityDeparture", required = false) Cities cityDeparture,
                             @RequestParam(value = "cityArrival", required = false) Cities cityArrival) {
        LOGGER.info("[INFO MESSAGE]: POST request to /api/ticket/select_city");

        addUserInfoToModel(model, principal);
        model.addAttribute("cities", Cities.values());
        model.addAttribute("selectedArrival", cityArrival);
        model.addAttribute("selectedDeparture", cityDeparture);
        return "main";
    }

    @Transactional
    @PostMapping("/api/ticket/save_ticket")
    public String addTicket(@ModelAttribute TicketDto ticketDto, Principal principal, Model model) {
        LOGGER.info("[INFO MESSAGE]: POST request to /api/ticket/save_ticket");

        String username = principal.getName();

        if (ticketDto.getDeparture() == null || ticketDto.getArrival() == null || ticketDto.getArrival().equals(ticketDto.getDeparture())) {
            model.addAttribute("errorMessage", "Please choose arrival and departure");
            return "main";
        }

        UserDto userDto = userService.getUserByUsername(username);
        ticketDto.setUser(UserMapper.mapToUser(userDto));
        ticketService.saveTicket(ticketDto);

        BigDecimal result = userDto.getBalance().subtract(BigDecimal.TEN); // stub!
        userService.updateUserBalance(username, result);

        model.addAttribute("successMessage", "Ticket was successfully purchased!");
        return "redirect:/main";
    }

    private void addUserInfoToModel(Model model, Principal principal) {
        if (principal != null) {
            UserDto userDto = userService.getUserByUsername(principal.getName());
            model.addAttribute("balance", userDto.getBalance());
        }
    }
}