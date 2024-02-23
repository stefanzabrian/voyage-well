package com.dev.voyagewell.repository.hotel;

import com.dev.voyagewell.model.hotel.Amenities;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AmenitiesRepository extends JpaRepository<Amenities, Integer> {
}
