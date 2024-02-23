package com.dev.voyagewell.controller.hotel;

import com.dev.voyagewell.controller.dto.hotel.HotelAddDto;
import com.dev.voyagewell.service.hotel.HotelService;
import com.dev.voyagewell.configuration.utils.exception.ErrorDetails;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@RestController
@RequestMapping("/hotel")
public class HotelController {
    private final HotelService hotelService;

    @Autowired
    public HotelController(HotelService hotelService) {
        this.hotelService = hotelService;
    }

    @PostMapping("/add")
    public ResponseEntity<?> addHotel(@Valid @RequestBody HotelAddDto hotelDto, WebRequest request) {
        try {
            hotelService.create(hotelDto);
            return ResponseEntity.status(HttpStatus.OK).body(new ErrorDetails(new Date(), "Hotel Added Successfully!", request.getDescription(false)));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorDetails(new Date(), e.getMessage(), request.getDescription(false)));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorDetails(new Date(), e.getMessage(), request.getDescription(false)));
        }
    }
}


