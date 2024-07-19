package com.andersen.ticketToRide.mapper;

import com.andersen.ticketToRide.dto.TicketDto;
import com.andersen.ticketToRide.model.Ticket;

public class TicketMapper {

    public static Ticket mapToTicket(TicketDto ticketDto) {
        return Ticket.builder()
                .id(ticketDto.getId())
                .departure(ticketDto.getDeparture())
                .arrival(ticketDto.getArrival())
                .segments(ticketDto.getSegments())
                .price(ticketDto.getPrice())
                .currency(ticketDto.getCurrency())
                .traveller_amount(ticketDto.getTraveller_amount())
                .traveller(ticketDto.getTraveller())
                .build();
    }

    public static TicketDto mapToTicketDto(Ticket ticket) {
        return TicketDto.builder()
                .id(ticket.getId())
                .departure(ticket.getDeparture())
                .arrival(ticket.getArrival())
                .segments(ticket.getSegments())
                .price(ticket.getPrice())
                .currency(ticket.getCurrency())
                .traveller_amount(ticket.getTraveller_amount())
                .traveller(ticket.getTraveller())
                .build();
    }
}