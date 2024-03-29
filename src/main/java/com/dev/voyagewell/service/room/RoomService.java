package com.dev.voyagewell.service.room;

import com.dev.voyagewell.configuration.utils.exception.ResourceNotFoundException;
import com.dev.voyagewell.controller.dto.room.RoomAddDto;
import com.dev.voyagewell.controller.dto.room.RoomDtoResponse;


import java.util.List;

public interface RoomService {
    void add (int id, RoomAddDto roomAddDto) throws ResourceNotFoundException;
    List<RoomDtoResponse> getAll(int id) throws ResourceNotFoundException;

    RoomDtoResponse getById(int id) throws ResourceNotFoundException;
    void update(int id, RoomDtoResponse roomDtoResponse) throws ResourceNotFoundException;
    void delete(int id) throws ResourceNotFoundException;


}
