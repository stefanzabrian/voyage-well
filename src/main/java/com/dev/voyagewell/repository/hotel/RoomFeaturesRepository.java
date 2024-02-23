package com.dev.voyagewell.repository.hotel;

import com.dev.voyagewell.model.hotel.RoomFeatures;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomFeaturesRepository extends JpaRepository<RoomFeatures,Integer> {
}
