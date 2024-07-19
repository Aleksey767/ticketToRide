package com.andersen.ticketToRide.service.impl;

import com.andersen.ticketToRide.dto.TravellerDto;
import com.andersen.ticketToRide.mapper.TravellerMapper;
import com.andersen.ticketToRide.model.Traveller;
import com.andersen.ticketToRide.repository.TravellerRepository;
import com.andersen.ticketToRide.service.TravellerService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TravellerServiceImpl implements TravellerService {
    @Autowired
    private TravellerRepository travellerRepository;

    @Override
    public void saveTraveller(TravellerDto travellerDto) {
        Traveller traveller = TravellerMapper.mapToTraveller(travellerDto);
        travellerRepository.save(traveller);
    }
    @Override
    public TravellerDto getTravellerById(long id){
        return TravellerMapper.mapToTravellerDto(travellerRepository.findById(id).get());
    }
}