package com.andersen.ticketToRide.mapper;

import com.andersen.ticketToRide.dto.TravellerDto;
import com.andersen.ticketToRide.model.Traveller;

public class TravellerMapper {

    public static Traveller mapToTraveller(TravellerDto traveller) {
        return Traveller.builder()
                .id(traveller.getId())
                .name(traveller.getName())
                .surname(traveller.getSurname())
                .phone(traveller.getPhone())
                .email(traveller.getEmail())
                .balance(traveller.getBalance())
                .build();
    }

    public static TravellerDto mapToTravellerDto(Traveller traveller) {
        return TravellerDto.builder()
                .id(traveller.getId())
                .name(traveller.getName())
                .surname(traveller.getSurname())
                .phone(traveller.getPhone())
                .email(traveller.getEmail())
                .balance(traveller.getBalance())
                .build();
    }
}
