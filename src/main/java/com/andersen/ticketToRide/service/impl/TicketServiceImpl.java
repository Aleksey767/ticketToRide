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
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementation of the {@link TicketService} interface that handles operations related to tickets.
 * This class interacts with the {@link TicketRepository} for ticket persistence and uses mappers
 * to convert between DTOs and model entities.
 */
@Service
@AllArgsConstructor
public class TicketServiceImpl implements TicketService {

    private static final Logger LOGGER = LoggerFactory.getLogger("com.andersen.ticketToRide");

    private final TicketRepository ticketRepository;

    private final TicketMapper ticketMapper;
    private final UserMapper userMapper;

    /**
     * Saves a ticket to the repository.
     * This method maps the {@link TicketDto} to a {@link Ticket} entity and then persists it using the repository.
     *
     * @param ticketDto a {@link TicketDto} object containing the ticket details to be saved
     */
    @Override
    public void saveTicket(TicketDto ticketDto) {
        LOGGER.debug("Saving ticket...");
        Ticket ticket = ticketMapper.toTicket(ticketDto);
        ticketRepository.save(ticket);
    }

    /**
     * Retrieves all tickets associated with a specific user.
     * This method maps the {@link UserDto} to a {@link User} entity, retrieves all tickets for that user from
     * the repository, and converts them to a list of {@link TicketDto} objects.
     *
     * @param userDto a {@link UserDto} object representing the user whose tickets are to be retrieved
     * @return a {@link List} of {@link TicketDto} objects representing the tickets associated with the user
     */
    @Override
    public List<TicketDto> getAllTicketsByUser(UserDto userDto) {
        LOGGER.debug("Getting all tickets by user...");
        User user = userMapper.toUser(userDto);
        List<Ticket> tickets = ticketRepository.findAllByUser(user);
        return tickets.stream()
                .map(ticketMapper::toTicketDto)
                .toList();
    }
}