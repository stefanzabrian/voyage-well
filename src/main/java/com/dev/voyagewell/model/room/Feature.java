package com.dev.voyagewell.model.room;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "feature")
@NoArgsConstructor
@Getter
@Setter
public class Feature {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "is_wifi")
    private boolean isWifi;
    @Column(name = "is_balcony")
    private boolean isBalcony;
    @Column(name = "is_bathroom")
    private boolean isBathroom;
    @Column(name = "is_tv")
    private boolean isTv;
    @Column(name = "is_air_conditioning")
    private boolean isAirConditioning;
    @Column(name = "is_heat")
    private boolean isHeat;
    @Column(name = "is_room_service")
    private boolean isRoomService;


    public Feature(boolean isWifi, boolean isBalcony, boolean isBathroom, boolean isTv, boolean isAirConditioning, boolean isHeat, boolean isRoomService) {
        this.isWifi = isWifi;
        this.isBalcony = isBalcony;
        this.isBathroom = isBathroom;
        this.isTv = isTv;
        this.isAirConditioning = isAirConditioning;
        this.isHeat = isHeat;
        this.isRoomService = isRoomService;
    }

    @Override
    public String toString() {
        return "Feature{" +
                "id=" + id +
                ", isWifi=" + isWifi +
                ", isBalcony=" + isBalcony +
                ", isBathroom=" + isBathroom +
                ", isTv=" + isTv +
                ", isAirConditioning=" + isAirConditioning +
                ", isHeat=" + isHeat +
                ", isRoomService=" + isRoomService +
                '}';
    }
}
