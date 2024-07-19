package com.andersen.ticketToRide.service;

import com.andersen.ticketToRide.dto.TravellerDto;
import com.andersen.ticketToRide.model.Traveller;

public interface TravellerService {
    void saveTraveller(TravellerDto travellerDto);
    TravellerDto getTravellerById(long id);
}