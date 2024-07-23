package com.andersen.ticketToRide.controller;

import com.andersen.ticketToRide.dto.TicketDto;
import com.andersen.ticketToRide.dto.UserDto;
import com.andersen.ticketToRide.enums.Cities;
import com.andersen.ticketToRide.graph.CalculatePrice;
import com.andersen.ticketToRide.mapper.UserMapper;
import com.andersen.ticketToRide.service.TicketService;
import com.andersen.ticketToRide.service.UserService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
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

    private final TicketService ticketService;

    private final UserService userService;

    @GetMapping("/main")
    public String buyTicket(Model model, Principal principal) {
        LOGGER.info("[INFO MESSAGE]: GET request to /main");

        getUserInfo(model, principal);
        model.addAttribute("cities", Cities.values());
        model.addAttribute("numbers", IntStream.rangeClosed(1, 10).mapToObj(BigDecimal::valueOf).collect(Collectors.toList()));
        return "main";
    }

    @PostMapping("/api/ticket/calculate_price")
    public String selectCity(Model model, Principal principal,
                             @RequestParam(value = "cityDeparture", required = false) Cities cityDeparture,
                             @RequestParam(value = "cityArrival", required = false) Cities cityArrival,
                             @RequestParam(value = "travellerAmount", required = false) BigDecimal travellerAmount) {
        LOGGER.info("[INFO MESSAGE]: POST request to /api/ticket/calculate_price");

        getUserInfo(model, principal);
        model.addAttribute("selectedArrival", cityArrival);
        model.addAttribute("selectedDeparture", cityDeparture);
        model.addAttribute("selectedTravellerAmount", travellerAmount);
        model.addAttribute("cities", Cities.values());
        if (travellerAmount != null && cityArrival != null && cityDeparture != null) {
            model.addAttribute("price", calculatePrice(cityDeparture, cityArrival).multiply(travellerAmount));
        }

        return "main";
    }

    @Transactional
    @PostMapping("/api/ticket/save_ticket")
    public String addTicket(@ModelAttribute TicketDto ticketDto, Principal principal, RedirectAttributes redirectAttributes) {
        LOGGER.info("[INFO MESSAGE]: POST request to /api/ticket/save_ticket");

        String username = principal.getName();

        if (ticketDto.getDeparture() == null || ticketDto.getArrival() == null || ticketDto.getArrival().equals(ticketDto.getDeparture())) {
            redirectAttributes.addFlashAttribute("errorMessage", "Please choose arrival and departure");
            return "redirect:/main";
        }

        UserDto userDto = userService.getUserByUsername(username);
        if (userDto.getBalance().compareTo(ticketDto.getPrice()) < 0) {
            redirectAttributes.addFlashAttribute("errorMessage", "You don't have enough money");
            return "redirect:/main";
        }
        userService.updateUserBalance(username, userDto.getBalance().subtract(ticketDto.getPrice()));

        ticketDto.setUser(UserMapper.mapToUser(userDto));
        ticketService.saveTicket(ticketDto);

        redirectAttributes.addFlashAttribute("successMessage", "Ticket was successfully purchased!");
        return "redirect:/main";
    }

    private void getUserInfo(Model model, Principal principal) {
        if (principal != null) {
            UserDto userDto = userService.getUserByUsername(principal.getName());
            model.addAttribute("balance", userDto.getBalance());
        }
    }

    private BigDecimal calculatePrice(Cities cityDeparture, Cities cityArrival) {
        if (cityDeparture != null && cityArrival != null) {
            CalculatePrice calculatePrice = new CalculatePrice();
            return new BigDecimal(calculatePrice.startCalculationProcess(cityDeparture, cityArrival));
        }
        return null;
    }
}