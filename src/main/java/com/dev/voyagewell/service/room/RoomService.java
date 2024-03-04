package com.dev.voyagewell.service.room;

import com.dev.voyagewell.configuration.utils.exception.ResourceNotFoundException;
import com.dev.voyagewell.controller.dto.room.RoomAddDto;

public interface RoomService {
    void add (int id, RoomAddDto roomAddDto) throws ResourceNotFoundException;

}
