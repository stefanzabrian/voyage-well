package com.dev.voyagewell.controller.room;

import com.dev.voyagewell.configuration.utils.exception.ErrorDetails;
import com.dev.voyagewell.configuration.utils.exception.ResourceNotFoundException;
import com.dev.voyagewell.controller.dto.room.RoomAddDto;
import com.dev.voyagewell.controller.dto.room.RoomDtoResponse;
import com.dev.voyagewell.service.room.RoomService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@RestController
@RequestMapping("/room")
public class RoomController {
    private static final Logger logger = LoggerFactory.getLogger(RoomController.class);
    private final RoomService roomService;

    @Autowired
    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @PostMapping("/add/{id}")
    public ResponseEntity<?> addRoomByHotelId(@PathVariable(name = "id") int id, @Valid @RequestBody RoomAddDto roomAddDto, WebRequest request) {
        try {
            roomService.add(id, roomAddDto);
            logger.info("Controller: Room added successfully for hotel ID: {}", id);
            return ResponseEntity.status(HttpStatus.OK).body(new ErrorDetails(new Date(), "Room Added Successfully!", request.getDescription(false)));
        } catch (ResourceNotFoundException e) {
            logger.error("Controller: Error adding room with ID {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorDetails(new Date(), e.getMessage(), request.getDescription(false)));
        } catch (RuntimeException e) {
            logger.error("Controller: Internal server error while adding room with ID {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorDetails(new Date(), e.getMessage(), request.getDescription(false)));
        }
    }

    @GetMapping("/all/{id}")
    public ResponseEntity<?> allRoomsByHotel(@PathVariable(name = "id") int id, WebRequest request) {
        try {
            logger.info("Controller: Fetching all rooms for hotel ID: {}", id);
            return ResponseEntity.status(HttpStatus.OK).body(roomService.getAll(id));
        } catch (ResourceNotFoundException e) {
            logger.error("Controller: Error fetching rooms for hotel ID {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorDetails(new Date(), e.getMessage(), request.getDescription(false)));
        } catch (RuntimeException e) {
            logger.error("Controller: Internal server error while fetching rooms for hotel ID {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorDetails(new Date(), e.getMessage(), request.getDescription(false)));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable(value = "id") int id, WebRequest request) {
        try {
            logger.info("Controller: Fetching room details for room ID: {}", id);
            return ResponseEntity.status(HttpStatus.OK).body(roomService.getById(id));
        } catch (ResourceNotFoundException e) {
            logger.error("Controller: Error fetching room details for room ID {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorDetails(new Date(), e.getMessage(), request.getDescription(false)));
        } catch (RuntimeException e) {
            logger.error("Controller: Internal server error while fetching room details for room ID {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorDetails(new Date(), e.getMessage(), request.getDescription(false)));
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> roomUpdate(@PathVariable(name = "id") int id, @RequestBody @Valid RoomDtoResponse roomDtoResponse, WebRequest request) {
        try {
            roomService.update(id, roomDtoResponse);
            logger.info("Controller: Updated room details for room ID: {}", id);
            return ResponseEntity.status(HttpStatus.OK).body(new ErrorDetails(new Date(), "Room Added Successfully!", request.getDescription(false)));
        } catch (ResourceNotFoundException e) {
            logger.error("Controller: Error fetching room details for room ID {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorDetails(new Date(), e.getMessage(), request.getDescription(false)));
        } catch (RuntimeException e) {
            logger.error("Controller: Internal server error while updating room details for room ID {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorDetails(new Date(), e.getMessage(), request.getDescription(false)));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRoomById(@PathVariable(value = "id") int id, WebRequest request) {
        try {
            roomService.delete(id);
            logger.info("Controller: Deleted room, for room ID: {}", id);
            return ResponseEntity.status(HttpStatus.OK).body(new ErrorDetails(new Date(), "Room Deleted Successfully! Id: " +id , request.getDescription(false)));
        } catch (ResourceNotFoundException e) {
            logger.error("Controller: Error fetching room details for room ID {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorDetails(new Date(), e.getMessage(), request.getDescription(false)));
        } catch (RuntimeException e){
            logger.error("Controller: Internal server error while updating room details for room ID {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorDetails(new Date(), e.getMessage(), request.getDescription(false)));
        }
    }
}
