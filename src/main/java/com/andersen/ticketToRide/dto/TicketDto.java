package com.andersen.ticketToRide.dto;

import com.andersen.ticketToRide.enums.Cities;
import com.andersen.ticketToRide.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TicketDto {

    private long id;

    private Cities departure;

    private Cities arrival;

    private BigDecimal price;

    private String currency = "GBP";

    private int travellerAmount;

    private User user;
}