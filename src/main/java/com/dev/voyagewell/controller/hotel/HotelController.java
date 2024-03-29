package com.dev.voyagewell.controller.hotel;

import com.dev.voyagewell.configuration.utils.exception.ResourceNotFoundException;
import com.dev.voyagewell.controller.dto.hotel.HotelAddDto;
import com.dev.voyagewell.model.hotel.Hotel;
import com.dev.voyagewell.service.hotel.HotelService;
import com.dev.voyagewell.configuration.utils.exception.ErrorDetails;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/hotel")
public class HotelController {
    private final HotelService hotelService;

    @Autowired
    public HotelController(HotelService hotelService) {
        this.hotelService = hotelService;
    }

    @PostMapping("/add")
    public ResponseEntity<?> addHotel(@Valid @RequestBody HotelAddDto hotelAddDto, WebRequest request) {
        try {
            hotelService.create(hotelAddDto);
            return ResponseEntity.status(HttpStatus.OK).body(new ErrorDetails(new Date(), "Hotel Added Successfully!", request.getDescription(false)));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorDetails(new Date(), e.getMessage(), request.getDescription(false)));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorDetails(new Date(), e.getMessage(), request.getDescription(false)));
        }
    }

    @GetMapping("/all")
    public ResponseEntity<?> allHotel(WebRequest request) {
        try {
            List<Hotel> hotelList = hotelService.getAll();
            return ResponseEntity.status(HttpStatus.OK).body(hotelList);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ErrorDetails(new Date(), e.getMessage(), request.getDescription(false)));
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getHotelById(@PathVariable(value = "id") int id, WebRequest request) {
        try {
            Hotel hotel = hotelService.getHotelById(id);
            return ResponseEntity.status(HttpStatus.OK).body(hotel);
        } catch (ResourceNotFoundException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorDetails(new Date(), e.getMessage(), request.getDescription(false)));
        }
    }
    @PostMapping("/{id}")
    public ResponseEntity<?> updateHotel(@PathVariable(value = "id") int id, @RequestBody Hotel hotel, WebRequest request){
        try {
            hotelService.update(id, hotel);
            return ResponseEntity.status(HttpStatus.OK).body(new ErrorDetails(new Date(), "Hotel Updated!", request.getDescription(false)));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorDetails(new Date(), e.getMessage(), request.getDescription(false)));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorDetails(new Date(), e.getMessage(), request.getDescription(false)));
        }
    }
}


