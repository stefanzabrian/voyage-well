package com.dev.voyagewell.service.hotel;

import com.dev.voyagewell.configuration.utils.exception.ResourceNotFoundException;
import com.dev.voyagewell.controller.dto.hotel.HotelDto;
import com.dev.voyagewell.model.hotel.Hotel;

import java.util.List;

public interface HotelService {
    void create(HotelDto hotelDto);
    List<Hotel> getAll() throws ResourceNotFoundException;
    HotelDto getHotelById(int id) throws ResourceNotFoundException;
}
