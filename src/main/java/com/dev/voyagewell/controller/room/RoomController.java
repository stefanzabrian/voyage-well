package com.dev.voyagewell.controller.room;

import com.dev.voyagewell.configuration.utils.exception.ErrorDetails;
import com.dev.voyagewell.configuration.utils.exception.ResourceNotFoundException;
import com.dev.voyagewell.controller.dto.room.RoomAddDto;
import com.dev.voyagewell.service.room.RoomService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@RestController
@RequestMapping("/room")
public class RoomController {
    private final RoomService roomService;

    @Autowired
    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @PostMapping("/add/{id}")
    public ResponseEntity<?> addRoom(@PathVariable(name = "id") int id, @Valid @RequestBody RoomAddDto roomAddDto, WebRequest request) {
        try {
            roomService.add(id, roomAddDto);
            return ResponseEntity.status(HttpStatus.OK).body(new ErrorDetails(new Date(), "Room Added Successfully!", request.getDescription(false)));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorDetails(new Date(), e.getMessage(), request.getDescription(false)));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorDetails(new Date(), e.getMessage(), request.getDescription(false)));
        }
    }
}
