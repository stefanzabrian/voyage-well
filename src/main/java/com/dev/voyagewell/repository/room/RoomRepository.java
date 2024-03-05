package com.dev.voyagewell.repository.room;

import com.dev.voyagewell.model.room.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoomRepository extends JpaRepository<Room, Integer> {
    Optional<List<Room>> findAllByHotelId(int id);
}
