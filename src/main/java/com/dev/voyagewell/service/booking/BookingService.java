package com.dev.voyagewell.service.booking;

import com.dev.voyagewell.configuration.utils.exception.ResourceNotFoundException;
import com.dev.voyagewell.controller.dto.booking.AddBookingDto;
import com.dev.voyagewell.controller.dto.calendar.CalendarDto;

import java.security.Principal;
import java.util.List;

public interface BookingService {
    void createBooking(int roomId, AddBookingDto addBookingDto, Principal principal) throws ResourceNotFoundException;

    List<CalendarDto> getCalendarByRoomIdAndMonthAndYear(int roomId, int month, int year) throws ResourceNotFoundException;
}
