package com.andersen.ticketToRide.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class TravellerDto {

    private long id;
    @NotEmpty(message = "name title should not be empty")
    private String name;
    @NotEmpty(message = "surname title should not be empty")
    private String surname;
    @NotEmpty(message = "phone title should not be empty")
    private String phone;
    @NotEmpty(message = "email title should not be empty")
    private String email;
    @NotEmpty(message = "balance title should not be empty")
    private BigDecimal balance;
}