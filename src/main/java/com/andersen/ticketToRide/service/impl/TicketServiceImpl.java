package com.andersen.ticketToRide.service.impl;

import com.andersen.ticketToRide.dto.TicketDto;
import com.andersen.ticketToRide.dto.UserDto;
import com.andersen.ticketToRide.enums.Cities;
import com.andersen.ticketToRide.graph.CalculatePrice;
import com.andersen.ticketToRide.mapper.TicketMapper;
import com.andersen.ticketToRide.mapper.UserMapper;
import com.andersen.ticketToRide.model.Ticket;
import com.andersen.ticketToRide.model.User;
import com.andersen.ticketToRide.repository.TicketRepository;
import com.andersen.ticketToRide.repository.UserRepository;
import com.andersen.ticketToRide.service.TicketService;
import com.andersen.ticketToRide.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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

    private final UserRepository userRepository;

    @Transactional
    @Override
    public ResponseEntity<?> addNewTicket(TicketDto ticketDto, Principal principal) {

        String username = principal.getName();
        HttpHeaders headers = new HttpHeaders();

        if (ticketDto.getArrival().equals(ticketDto.getDeparture())) {
            headers.add("X-Redirect", "invalid_data");
            return new ResponseEntity<>(ticketDto, headers, HttpStatus.CREATED);
        }

        Optional<User> user = userRepository.findByUsername(username);
        UserDto userDto = user.map(userMapper::toUserDto).orElse(null);

        assert userDto != null;

        if (userDto.getBalance().compareTo(ticketDto.getPrice()) < 0) {
            headers.add("X-Redirect", "out_of_money");
            return new ResponseEntity<>(ticketDto, headers, HttpStatus.CREATED);
        }

        userRepository.updateBalance(username, userDto.getBalance().subtract(ticketDto.getPrice()));

        ticketDto.setUser(userMapper.toUser(userDto));
        saveTicket(ticketDto);

        headers.add("X-Redirect", "success");
        return new ResponseEntity<>(ticketDto, headers, HttpStatus.CREATED);
    }

    private BigDecimal callDijkstraAlgorithm(Cities cityDeparture, Cities cityArrival) {
        CalculatePrice calculatePrice = new CalculatePrice();
        return new BigDecimal(calculatePrice.startCalculationProcess(cityDeparture, cityArrival));
    }

    @Override
    public ResponseEntity<?> calculateTicketPrice(Cities departure, Cities arrival, int travellerAmount) {

        if (departure != null && arrival != null && travellerAmount != 0) {
            ObjectMapper mapper = new ObjectMapper();
            ObjectNode jsonObject = mapper.createObjectNode();
            jsonObject.put("departure", departure.toString());
            jsonObject.put("arrival", arrival.toString());
            jsonObject.put("travellerAmount", travellerAmount);
            jsonObject.put("price", callDijkstraAlgorithm(departure, arrival).multiply(BigDecimal.valueOf(travellerAmount)));
            return ResponseEntity.ok().body(jsonObject);
        } else return (ResponseEntity<?>) ResponseEntity.badRequest();
    }

    @Override
    public void redirectAfterCalculating(String status, RedirectAttributes redirectAttributes) {
        if (status.equals("invalid_data")) {
            redirectAttributes.addFlashAttribute("errorMessage", "You should pick arrival,departure and traveller amount");
        } else if (status.equals("out_of_money")) {
            redirectAttributes.addFlashAttribute("errorMessage", "You don't have enough money");
        } else {
            redirectAttributes.addFlashAttribute("successMessage", "Ticket was successfully purchased");
        }
    }

    @Override
    public void getTicketInfo(Model model, Principal principal) {
        model.addAttribute("cities", Cities.values());
        model.addAttribute("numbers", IntStream.rangeClosed(1, 10).mapToObj(BigDecimal::valueOf).collect(Collectors.toList()));
    }

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