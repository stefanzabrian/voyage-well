package com.dev.voyagewell.repository.room;

import com.dev.voyagewell.model.room.Feature;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeatureRepository extends JpaRepository<Feature, Integer> {
}
