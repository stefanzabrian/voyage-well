package com.dev.voyagewell.repository.calendar;

import com.dev.voyagewell.model.calendar.BookingCalendar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingCalendarRepository extends JpaRepository<BookingCalendar, Integer> {
}
