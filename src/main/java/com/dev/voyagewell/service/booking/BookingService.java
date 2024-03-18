package com.dev.voyagewell.service.booking;

import com.dev.voyagewell.configuration.utils.exception.ResourceNotFoundException;
import com.dev.voyagewell.controller.dto.booking.AddBookingDto;

import java.security.Principal;

public interface BookingService {
    void createBooking(int roomId, AddBookingDto addBookingDto, Principal principal) throws ResourceNotFoundException;
}
