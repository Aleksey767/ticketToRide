package com.andersen.ticketToRide.dto;

import com.andersen.ticketToRide.enums.Cities;
import com.andersen.ticketToRide.model.Traveller;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class TicketDto {

    private long id;

    @NotEmpty(message = "departure title should not be empty")
    private Cities departure;

    @NotEmpty(message = "arrival title should not be empty")
    private Cities arrival;

    @NotEmpty(message = "segments title should not be empty")
    private int segments;

    @NotEmpty(message = "price title should not be empty")
    private BigDecimal price;

    @NotEmpty(message = "currency title should not be empty")
    private String currency;

    @NotEmpty(message = "traveller_amount title should not be empty")
    private int traveller_amount;

    @NotEmpty(message = "traveller title should not be empty")
    private Traveller traveller;
}
