package com.andersen.ticketToRide.controller;

import com.andersen.ticketToRide.dto.TicketDto;
import com.andersen.ticketToRide.dto.TravellerDto;
import com.andersen.ticketToRide.mapper.TravellerMapper;
import com.andersen.ticketToRide.service.TicketService;
import com.andersen.ticketToRide.service.TravellerService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@AllArgsConstructor
@ResponseBody
public class MainController {

    private TicketService ticketService;

    private TravellerService travellerService;

    @PostMapping("/add_ticket")
    public TicketDto addTicket(@RequestBody TicketDto ticketDto) {
        long id = ticketDto.getTraveller().getId();
        ticketDto.setTraveller(TravellerMapper.mapToTraveller(travellerService.getTravellerById(id)));
        ticketService.saveTicket(ticketDto);
        return ticketDto;
    }


    @PostMapping("/add_traveller")
    public TravellerDto saveTraveller(@RequestBody TravellerDto travellerDto) {
        travellerService.saveTraveller(travellerDto);
        return travellerDto;
    }
    @GetMapping("/main")
    public String mainPage() {
        return "Hello World!!!";
    }
}