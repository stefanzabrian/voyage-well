package com.dev.voyagewell.model.hotel;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "amenities")
@NoArgsConstructor
@Getter
@Setter
public class Amenities {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "is_free_parking")
    private boolean isFreeParking;
    @Column(name = "is_restaurant")
    private boolean isRestaurant;
    @Column(name = "is_bar")
    private boolean isBar;
    @Column(name = "is_spa")
    private boolean isSpa;
    @Column(name = "is_wifi")
    private boolean isWifi;

    public Amenities(boolean isFreeParking, boolean isRestaurant, boolean isBar, boolean isSpa, boolean isWifi) {
        this.isFreeParking = isFreeParking;
        this.isRestaurant = isRestaurant;
        this.isBar = isBar;
        this.isSpa = isSpa;
        this.isWifi = isWifi;
    }

    @Override
    public String toString() {
        return "Amenities{" +
                "id=" + id +
                ", isFreeParking=" + isFreeParking +
                ", isRestaurant=" + isRestaurant +
                ", isBar=" + isBar +
                ", isSpa=" + isSpa +
                ", isWifi=" + isWifi +
                '}';
    }
}
