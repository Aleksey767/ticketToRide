package com.andersen.ticketToRide.controller;

import com.andersen.ticketToRide.dto.TicketDto;
import com.andersen.ticketToRide.enums.Cities;
import com.andersen.ticketToRide.service.TicketService;
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

@RequestMapping("/api/v1/tickets")
@Controller
@AllArgsConstructor
public class TicketController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TicketController.class);

    private final TicketService ticketService;

    private final UserService userService;

    @GetMapping
    public String buyTicket(Model model, Principal principal) {
        LOGGER.info("GET request to /api/v1/tickets");

        userService.getUserInfo(model, principal);
        ticketService.getTicketInfo(model, principal);

        return "main";
    }

    @GetMapping("/redirect")
    public String invalidData(@RequestParam String status, RedirectAttributes redirectAttributes) {
        LOGGER.info("GET request to /api/v1/tickets/redirect");

        ticketService.redirectAfterCalculating(status, redirectAttributes);

        return "redirect:/api/v1/tickets";
    }

    @PostMapping("/calculation")
    public ResponseEntity<?> calculatePrice(@RequestParam String departure,
                                            @RequestParam String arrival,
                                            @RequestParam String travellerAmount) {

        LOGGER.info("POST request to /api/v1/tickets/calculation");

        return ticketService.calculateTicketPrice(Cities.valueOf(departure), Cities.valueOf(arrival), Integer.parseInt(travellerAmount));
    }

    @PostMapping("/saving")
    public ResponseEntity<?> addTicket(@RequestBody TicketDto ticketDto, Principal principal) {

        LOGGER.info("POST request to /api/v1/tickets/saving");

        return ticketService.addNewTicket(ticketDto, principal);
    }
}