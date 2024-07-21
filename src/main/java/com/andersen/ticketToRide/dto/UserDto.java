package com.andersen.ticketToRide.dto;

import com.andersen.ticketToRide.enums.Roles;
import lombok.*;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserDto {

    private long id;

    private String username;

    private String password;

    private Roles role = Roles.USER;

    private BigDecimal balance = new BigDecimal("100");

    private String realName;
}