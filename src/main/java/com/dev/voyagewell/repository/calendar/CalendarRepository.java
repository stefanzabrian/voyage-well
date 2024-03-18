package com.dev.voyagewell.repository.calendar;

import com.dev.voyagewell.model.calendar.Calendar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface CalendarRepository extends JpaRepository<Calendar, Integer> {
    List<Calendar> findAllByRoomId(int roomId);
    Optional<Calendar> findByRoomIdAndDate(int roomId, Date date);
    List<Calendar> findByRoomIdAndDateBetween(int roomId, Date checkInDate, Date checkOutDate);
}
