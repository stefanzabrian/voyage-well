package com.dev.voyagewell.repository.hotel;

import com.dev.voyagewell.model.hotel.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Integer> {
    Optional<Hotel> findByName(String hotelName);
}
