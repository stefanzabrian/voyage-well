package com.dev.voyagewell.service.hotel;

import com.dev.voyagewell.configuration.utils.exception.ResourceNotFoundException;
import com.dev.voyagewell.controller.dto.hotel.HotelAddDto;
import com.dev.voyagewell.model.hotel.Hotel;

import java.util.List;

public interface HotelService {
    void create(HotelAddDto hotelAddDto);
    List<Hotel> getAll() throws ResourceNotFoundException;
    Hotel getHotelById(int id) throws ResourceNotFoundException;
    void update(int id, Hotel hotel) throws ResourceNotFoundException;
}
