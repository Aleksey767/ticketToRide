package com.andersen.ticketToRide.service.impl;

import com.andersen.ticketToRide.dto.TicketDto;
import com.andersen.ticketToRide.dto.UserDto;
import com.andersen.ticketToRide.mapper.TicketMapper;
import com.andersen.ticketToRide.mapper.UserMapper;
import com.andersen.ticketToRide.model.Ticket;
import com.andersen.ticketToRide.model.User;
import com.andersen.ticketToRide.repository.TicketRepository;
import com.andersen.ticketToRide.service.TicketService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TicketServiceImpl implements TicketService {

    private static final Logger LOGGER = LoggerFactory.getLogger("com.andersen.ticketToRide");

    private TicketRepository ticketRepository;

    @Override
    public void saveTicket(TicketDto ticketDto) {
        LOGGER.debug("[DEBUG MESSAGE]: Saving ticket..");
        Ticket ticket = TicketMapper.mapToTicket(ticketDto);
        ticketRepository.save(ticket);
    }

    @Override
    public List<TicketDto> getAllTicketsByUser(UserDto userDto) {
        LOGGER.debug("[DEBUG MESSAGE]: Getting all tickets by user...");
        User user = UserMapper.mapToUser(userDto);
        List<Ticket> tickets = ticketRepository.findAllByUser(user);
        return tickets.stream()
                .map(TicketMapper::mapToTicketDto)
                .toList();
    }
}