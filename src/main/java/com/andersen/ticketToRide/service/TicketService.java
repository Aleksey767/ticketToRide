package com.andersen.ticketToRide.service;

import com.andersen.ticketToRide.dto.TicketDto;
import com.andersen.ticketToRide.dto.UserDto;

import java.util.List;

public interface TicketService {
    void saveTicket(TicketDto ticketDto);
    List<TicketDto> getAllTicketsByUser(UserDto userDto);
}