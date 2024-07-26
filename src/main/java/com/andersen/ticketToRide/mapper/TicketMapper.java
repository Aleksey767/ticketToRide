package com.andersen.ticketToRide.mapper;

import com.andersen.ticketToRide.dto.TicketDto;
import com.andersen.ticketToRide.model.Ticket;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TicketMapper {

    TicketDto toTicketDto(Ticket ticket);
    Ticket toTicket(TicketDto ticketDto);
}
