package com.dev.voyagewell.repository.calendar;

import com.dev.voyagewell.model.calendar.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Integer> {
}
