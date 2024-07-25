package com.andersen.ticketToRide.controller;

import com.andersen.ticketToRide.dto.TicketDto;
import com.andersen.ticketToRide.dto.UserDto;
import com.andersen.ticketToRide.enums.Cities;
import com.andersen.ticketToRide.graph.CalculatePrice;
import com.andersen.ticketToRide.mapper.UserMapper;
import com.andersen.ticketToRide.service.TicketService;
import com.andersen.ticketToRide.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.security.Principal;

@Controller
@AllArgsConstructor
public class TicketController {

    private static final Logger LOGGER = LoggerFactory.getLogger(MainController.class);

    private final UserService userService;

    private final TicketService ticketService;

    @PostMapping("/api/ticket/calculate_price")
    public ResponseEntity<?> calculatePrice(@RequestParam String departure, @RequestParam String arrival, @RequestParam String travellerAmount) {
        LOGGER.info("POST request to /api/ticket/calculate_price");

        if (!departure.isEmpty() && !arrival.isEmpty() && !travellerAmount.isEmpty()) {
            ObjectMapper mapper = new ObjectMapper();
            ObjectNode jsonObject = mapper.createObjectNode();
            jsonObject.put("departure", departure);
            jsonObject.put("arrival", arrival);
            jsonObject.put("travellerAmount", travellerAmount);
            jsonObject.put("price",
                    calculatePrice(Cities.valueOf(departure), Cities.valueOf(arrival)).multiply(BigDecimal.valueOf(Integer.parseInt(travellerAmount))));
            return ResponseEntity.ok().body(jsonObject);
        } else return (ResponseEntity<?>) ResponseEntity.badRequest();
    }

    @Transactional
    @PostMapping("/api/ticket/save_ticket")
    public ResponseEntity<?> addTicket(@RequestParam String departure, @RequestParam String arrival,
                                       @RequestParam String travellerAmount, @RequestParam String price, Principal principal) {
        LOGGER.info("POST request to /api/ticket/save_ticket");

        String username = principal.getName();
        HttpHeaders headers = new HttpHeaders();

        TicketDto ticketDto = TicketDto.builder()
                .departure(Cities.valueOf(departure))
                .arrival(Cities.valueOf(arrival))
                .price(new BigDecimal(price))
                .currency("GBP")
                .travellerAmount(Integer.parseInt(travellerAmount))
                .build();

        if (departure == null || arrival == null || arrival.equals(departure)) {
            headers.add("X-Redirect", "invalid_data");
            return new ResponseEntity<>(ticketDto, headers, HttpStatus.CREATED);
        }

        UserDto userDto = userService.getUserByUsername(username);
        if (userDto.getBalance().compareTo(ticketDto.getPrice()) < 0) {
            headers.add("X-Redirect", "out_of_money");
            return new ResponseEntity<>(ticketDto, headers, HttpStatus.CREATED);
        }

        userService.updateUserBalance(username, userDto.getBalance().subtract(ticketDto.getPrice()));

        ticketDto.setUser(UserMapper.mapToUser(userDto));
        ticketService.saveTicket(ticketDto);

        headers.add("X-Redirect", "success");
        return new ResponseEntity<>(ticketDto, headers, HttpStatus.CREATED);
    }

    private BigDecimal calculatePrice(Cities cityDeparture, Cities cityArrival) {
        if (cityDeparture != null && cityArrival != null) {
            CalculatePrice calculatePrice = new CalculatePrice();
            return new BigDecimal(calculatePrice.startCalculationProcess(cityDeparture, cityArrival));
        }
        return null;
    }
}