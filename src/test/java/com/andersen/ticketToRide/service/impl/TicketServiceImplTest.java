package com.andersen.ticketToRide.service.impl;

import com.andersen.ticketToRide.dto.TicketDto;
import com.andersen.ticketToRide.dto.UserDto;
import com.andersen.ticketToRide.mapper.TicketMapper;
import com.andersen.ticketToRide.mapper.UserMapper;
import com.andersen.ticketToRide.model.Ticket;
import com.andersen.ticketToRide.model.User;
import com.andersen.ticketToRide.repository.TicketRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TicketServiceImplTest {

    @Mock
    private TicketRepository ticketRepository;

    @InjectMocks
    private TicketServiceImpl ticketService;

    private User user;
    private Ticket ticket;
    private UserDto userDto;
    private TicketDto ticketDto;

    @BeforeEach
    void init() {
        userDto = mock(UserDto.class);
        ticketDto = mock(TicketDto.class);
        user = mock(User.class);
        ticket = mock(Ticket.class);
    }

    @Test
    void saveTicket_shouldCallRepository() {
        try (MockedStatic<TicketMapper> mockedStatic = Mockito.mockStatic(TicketMapper.class)) {
            mockedStatic.when(() -> TicketMapper.mapToTicket(ticketDto)).thenReturn(ticket);

            ticketService.saveTicket(ticketDto);

            verify(ticketRepository).save(ticket);
        }
    }

    @Test
    void getAllTicketsByUser_shouldCallRepository() {
        try (MockedStatic<UserMapper> mockedStaticUser = Mockito.mockStatic(UserMapper.class)) {
            mockedStaticUser.when(() -> UserMapper.mapToUser(userDto)).thenReturn(user);

            ticketService.getAllTicketsByUser(userDto);

            verify(ticketRepository).findAllByUser(user);
        }
    }

    @Test
    void getAllTicketsByUser_shouldReturnTicketsList() {
        try (MockedStatic<UserMapper> mockedStaticUser = Mockito.mockStatic(UserMapper.class);
             MockedStatic<TicketMapper> mockedStaticTicket = Mockito.mockStatic(TicketMapper.class)) {

            mockedStaticUser.when(() -> UserMapper.mapToUser(userDto)).thenReturn(user);

            List<Ticket> tickets = List.of(ticket);
            when(ticketRepository.findAllByUser(user)).thenReturn(tickets);

            TicketDto ticketDto = new TicketDto();
            mockedStaticTicket.when(() -> TicketMapper.mapToTicketDto(ticket)).thenReturn(ticketDto);

            List<TicketDto> ticketsDto = ticketService.getAllTicketsByUser(userDto);

            assertEquals(1, ticketsDto.size());
            assertEquals(ticketDto, ticketsDto.getFirst());

            verify(ticketRepository).findAllByUser(user);
        }
    }
}