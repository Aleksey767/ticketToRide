package com.andersen.ticketToRide.service;

import com.andersen.ticketToRide.dto.TicketDto;
import com.andersen.ticketToRide.dto.UserDto;

import java.util.List;

/**
 * The {@code TicketService} interface provides methods to manage ticket information in the ticket-to-ride application.
 * It allows for saving tickets and retrieving all tickets associated with a specific user.
 */
public interface TicketService {

    /**
     * Saves a new ticket to the postgre.
     *
     * @param ticketDto a {@link TicketDto} object containing the ticket details to be saved
     */
    void saveTicket(TicketDto ticketDto);

    /**
     * Retrieves all tickets associated with a specific user.
     *
     * @param userDto a {@link UserDto} object representing the user whose tickets are to be retrieved
     * @return a {@link List} of {@link TicketDto} objects representing the user's tickets
     */
    List<TicketDto> getAllTicketsByUser(UserDto userDto);
}
