package com.andersen.ticketToRide.mapper;

import com.andersen.ticketToRide.dto.TicketDto;
import com.andersen.ticketToRide.enums.Cities;
import com.andersen.ticketToRide.model.Ticket;
import com.andersen.ticketToRide.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class TicketMapperTest {

    @Autowired
    private TicketMapper ticketMapper;

    @Test
    void mapToTicket_shouldConvertDtoToModel() {

        TicketDto ticketDto = TicketDto.builder()
                .id(1L)
                .departure(Cities.SWINDEN)
                .arrival(Cities.LONDON)
                .price(new BigDecimal("99.99"))
                .currency("USD")
                .travellerAmount(2)
                .user(new User())
                .build();

        Ticket ticket = ticketMapper.toTicket(ticketDto);

        assertEquals(ticketDto.getId(), ticket.getId());
        assertEquals(ticketDto.getDeparture(), ticket.getDeparture());
        assertEquals(ticketDto.getArrival(), ticket.getArrival());
        assertEquals(ticketDto.getPrice(), ticket.getPrice());
        assertEquals(ticketDto.getCurrency(), ticket.getCurrency());
        assertEquals(ticketDto.getTravellerAmount(), ticket.getTravellerAmount());
        assertEquals(ticketDto.getUser(), ticket.getUser());
    }

    @Test
    void mapToTicketDto_shouldConvertModelToDto() {

        Ticket ticket = Ticket.builder()
                .id(1L)
                .departure(Cities.SWINDEN)
                .arrival(Cities.LONDON)
                .price(new BigDecimal("99.99"))
                .currency("USD")
                .travellerAmount(2)
                .user(new User())
                .build();

        TicketDto ticketDto = ticketMapper.toTicketDto(ticket);

        assertEquals(ticket.getId(), ticketDto.getId());
        assertEquals(ticket.getDeparture(), ticketDto.getDeparture());
        assertEquals(ticket.getArrival(), ticketDto.getArrival());
        assertEquals(ticket.getPrice(), ticketDto.getPrice());
        assertEquals(ticket.getCurrency(), ticketDto.getCurrency());
        assertEquals(ticket.getTravellerAmount(), ticketDto.getTravellerAmount());
        assertEquals(ticket.getUser(), ticketDto.getUser());
    }
}