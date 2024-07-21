package com.andersen.ticketToRide.controller;

import com.andersen.ticketToRide.dto.TicketDto;
import com.andersen.ticketToRide.dto.UserDto;
import com.andersen.ticketToRide.enums.Cities;
import com.andersen.ticketToRide.mapper.UserMapper;
import com.andersen.ticketToRide.service.TicketService;
import com.andersen.ticketToRide.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@AllArgsConstructor
public class MainController {

    private TicketService ticketService;

    private UserService userService;

    @GetMapping("/")
    public String redirectToMain() {

        return "redirect:/main";
    }

    @GetMapping("/main")
    public String mainPage(Model model,@RequestParam(value = "selectedCity", required = false) Cities selectedCity) {
        model.addAttribute("cities", Cities.values());
        model.addAttribute("selectedCity", selectedCity);
        return "main";
    }

    @GetMapping("/{username}")
    public String userProfile(@PathVariable String username, Model model) {
        UserDto userDto = userService.getUserByUsername(username);
        model.addAttribute("tickets", ticketService.getAllTicketsByUser(userDto));
        return "userPage";
    }

    @GetMapping("/new_ticket")
    public String newTicket(Model model) {
        model.addAttribute("ticketDto", new TicketDto());
        return "newTicket";
    }

    @PostMapping("/add_ticket")
    public String addTicket(@ModelAttribute TicketDto ticketDto, Principal principal) {
        UserDto userDto = userService.getUserByUsername(principal.getName());
        ticketDto.setUser(UserMapper.mapToUser(userDto));
        ticketService.saveTicket(ticketDto);
        return "redirect:/main";
    }
}