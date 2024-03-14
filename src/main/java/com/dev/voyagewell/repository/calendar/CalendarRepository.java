package com.dev.voyagewell.repository.calendar;

import com.dev.voyagewell.model.calendar.Calendar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CalendarRepository extends JpaRepository<Calendar, Integer> {
    List<Calendar> findAllByRoomId(int roomId);
}
