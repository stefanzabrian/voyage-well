package com.dev.voyagewell.controller.booking;

import com.dev.voyagewell.configuration.utils.exception.ErrorDetails;
import com.dev.voyagewell.configuration.utils.exception.ResourceNotFoundException;
import com.dev.voyagewell.controller.dto.booking.AddBookingDto;
import com.dev.voyagewell.controller.room.RoomController;
import com.dev.voyagewell.service.booking.BookingService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.security.Principal;
import java.util.Date;

@RestController
@RequestMapping("/booking")
public class BookingController {
    private static final Logger logger = LoggerFactory.getLogger(RoomController.class);
    private final BookingService bookingService;

    @Autowired
    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;

    }

    @PostMapping("/new/room-id-{id}")
    public ResponseEntity<?> newBooking(@PathVariable(value = "id") int roomId, @Valid @RequestBody AddBookingDto addBookingDto, Principal principal, WebRequest request) {
        try {
            bookingService.createBooking(roomId, addBookingDto, principal);
            logger.info("Booking Controller: Booking added successfully for room ID: {}", roomId);
            return ResponseEntity.status(HttpStatus.OK).body(new ErrorDetails(new Date(), "Booking Added Successfully!", request.getDescription(false)));
        } catch (ResourceNotFoundException e) {
            logger.error("Booking Controller: Error adding booking for room ID {}: {}", roomId, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorDetails(new Date(), e.getMessage(), request.getDescription(false)));
        } catch (RuntimeException e) {
            logger.error("Booking Controller: Internal server error while adding Booking for room with ID {}: {}", roomId, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorDetails(new Date(), e.getMessage(), request.getDescription(false)));
        }
    }

    @GetMapping("/calendar")
    public ResponseEntity<?> calendarByMonthAndYear(@RequestParam int roomId, @RequestParam int month, @RequestParam int year, WebRequest request) {
        try {
            logger.info("Booking Controller: Fetching Calendar for room ID: {}, Month : {}, Year : {}", roomId, month, year);
            return ResponseEntity.status(HttpStatus.OK).body(bookingService.getCalendarByRoomIdAndMonthAndYear(roomId, month, year));
        } catch (ResourceNotFoundException e){
            logger.error("Booking Controller: Fetching Calendar for room ID: {}, Month : {}, Year : {}, Error : {}", roomId, month, year ,e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorDetails(new Date(), e.getMessage(), request.getDescription(false)));
        }
    }

}
