package com.andersen.ticketToRide.dto;

import com.andersen.ticketToRide.enums.Cities;
import com.andersen.ticketToRide.model.User;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class TicketDto {

    private long id;

    @NotNull(message = "departure field should be present")
    private Cities departure;

    @NotNull(message = "arrival field should be present")
    private Cities arrival;

    @NotNull(message = "price field should be present")
    private BigDecimal price;

    @Builder.Default
    private String currency = "GBP";

    @NotNull(message = "travellerAmount field should be present")
    private int travellerAmount;

    private User user;
}